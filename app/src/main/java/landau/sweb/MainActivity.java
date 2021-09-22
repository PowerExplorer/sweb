package landau.sweb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import landau.sweb.utils.ExceptionLogger;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.DownloadListener;
import android.view.View.OnTouchListener;
import android.view.View.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.content.DialogInterface.OnDismissListener;
import java.lang.reflect.*;
import java.util.*;
import landau.sweb.MainActivity.*;
import android.print.*;
import android.support.annotation.*;
import android.graphics.*;
import java.io.*;
import android.widget.ImageButton;
import android.widget.*;
import android.content.*;
import android.os.storage.*;
import android.webkit.*;
import android.view.*;
import android.util.*;
import java.lang.ref.*;
import android.view.GestureDetector.*;
import android.content.res.*;
import android.os.*;
import java.net.*;
import android.text.*;
import java.sql.*;
import java.text.*;
import android.provider.*;

public class MainActivity extends Activity {
	 
    private static final String TAG = "MainActivity";
	private static final int DARK_BACKGROUND = 0xffc0c0c0;
	private static final int LIGHT_BACKGROUND = 0xfffffff0;
	
	private static final Pattern FAVICON_PATTERN = Pattern.compile(".*?/favicon\\.(ico|png)", Pattern.CASE_INSENSITIVE);
	
	private static final String IMAGE_PAT = ".*?\\.(gif|jpe?g|png|bmp|webp|tiff?|wmf|psd|pic|ico|svg).*?";
	private static final String MEDIA_PAT = ".*?\\.(avi|mp4|webm|wmv|asf|mkv|av1|mov|mpeg|flv|mp3|opus|wav|wma|amr|ogg|vp9|pcm|rm|ram).*?";
	private static final String CSS_PAT = ".*?css.*?";
	private static final String JAVASCRIPT_PAT = ".*?[\\./]js.*?";
	private static final String FONT_PAT = ".*?\\.(otf|ttf|ttc|woff|woff2).*?";
	private static final Pattern IMAGES_PATTERN = Pattern.compile(IMAGE_PAT, Pattern.CASE_INSENSITIVE);
	private static final Pattern MEDIA_PATTERN = Pattern.compile(MEDIA_PAT, Pattern.CASE_INSENSITIVE);
	private static final Pattern CSS_PATTERN = Pattern.compile(CSS_PAT, Pattern.CASE_INSENSITIVE);
	private static final Pattern JAVASCRIPT_PATTERN = Pattern.compile(JAVASCRIPT_PAT, Pattern.CASE_INSENSITIVE);
	private static final Pattern FONT_PATTERN = Pattern.compile(FONT_PAT, Pattern.CASE_INSENSITIVE);
    class EmptyOnClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface p1, int p2) {
		}
	}
//	public static String[] returnImageUrlsFromHtml(String htmlCode) {
//        List<String> imageSrcList = new ArrayList<String>();
//        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
//        Matcher m = p.matcher(htmlCode);
//        String quote = null;
//        String src = null;
//        while (m.find()) {
//            quote = m.group(1);
//            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
//            imageSrcList.add(src);
//        }
//        if (imageSrcList.size() == 0) {
//            return null;
//        }
//        return imageSrcList.toArray(new String[imageSrcList.size()]);
//    }
//	public class ImageJavascriptInterface {
//
//		private Context context;
//		private String[] imageUrls;    //Picture Collection
//
//		public ImageJavascriptInterface(Context context, String[] imageUrls) {
//			this.context = context;
//			this.imageUrls = imageUrls;
//		}
//
//		@JavascriptInterface
//		public void openImage(String image,int position) {
//			//todo jump to the picture view page
//		}
//	}
	private static class Tab {
        Tab(WebView w, boolean isIncognito) {
            this.webview = w;
			this.isIncognito = isIncognito;
        }
		boolean loading;
        WebView webview;
        boolean isDesktopUA;
		long lastDownload = -1L;
		String sourceName;
		boolean textChanged;
		boolean isIncognito = false;
		boolean skipTextChange = false;
		ArrayList<String> requestsLog = new ArrayList<>();
		Bitmap favicon;
		String favHref;
		boolean blockImages;
		boolean blockMedia;
		boolean blockFonts;
		boolean blockCSS;
		boolean blockJavaScript;
		String source;
		boolean saveMedia;
		LinkedList<Article> mediaList = new LinkedList<>();
		String includePatternStr;
		String excludePatternStr;
		Pattern includePattern;
		Pattern excludePattern;
		
		String includeUrlPatternStr;
		String excludeUrlPatternStr;
		Pattern includeUrlPattern;
		Pattern excludeUrlPattern;
	}
	
	private boolean FULL_INCOGNITO = Build.VERSION.SDK_INT >= 28;
	private boolean WEB_RTC = Build.VERSION.SDK_INT >= 21;
	private boolean THIRD_PARTY_COOKIE_BLOCKING = Build.VERSION.SDK_INT >= 21;
	
    static final String searchUrl = "https://www.google.com/search?q=%s";
    static final String searchCompleteUrl = "https://www.google.com/complete/search?client=firefox&q=%s";
    private static final String desktopUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";
	private static final String androidUA = "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Mobile Safari/537.36";
    static final String[] adblockRulesList = {
            "https://easylist.to/easylist/easylist.txt",
            "https://easylist.to/easylist/easyprivacy.txt",
            "https://pgl.yoyo.org/adservers/serverlist.php?hostformat=hosts&showintro=1&mimetype=plaintext",    // Peter's
            "https://easylist.to/easylist/fanboy-social.txt",
            "https://easylist-downloads.adblockplus.org/advblock.txt",  // RU AdList
    };

    static final int FORM_FILE_CHOOSER = 1;

    static final int PERMISSION_REQUEST_EXPORT_BOOKMARKS = 1;
    static final int PERMISSION_REQUEST_IMPORT_BOOKMARKS = 2;
    static final int PERMISSION_REQUEST_DOWNLOAD = 3;
	static final int PERMISSION_REQUEST_READ_EXTERNAL = 4;
	
    private ArrayList<Tab> tabs = new ArrayList<>();
    private int currentTabIndex;
    private FrameLayout webviews;
	private View main_layout;
	private ViewGroup address;
	private ListView requestList;
	private ImageView faviconImage;
	private AutoCompleteTextView et;
	private View searchPane;
	private ImageView goStop;
	private ProgressBar progressbar;
	private ViewGroup toolbar;
    private boolean isNightMode;
    private boolean isFullscreen;
    private SharedPreferences prefs;
    private boolean useAdBlocker;
    private AdBlocker adBlocker;
    private boolean isLogRequests;
    private final View[] fullScreenView = new View[1];
    private final WebChromeClient.CustomViewCallback[] fullScreenCallback = new WebChromeClient.CustomViewCallback[1];
    private EditText searchEdit;
    private TextView searchCount;
    private TextView txtTabCount;
	private ImageView blockImagesImageView;
	private ImageView searchFindPrev;
	private ImageView searchFindNext;
	private ImageView searchClose;
	
	private boolean blockImages;
    private boolean blockMedia;
	private boolean isFullMenu;
    private boolean enableCookies;
	private boolean saveHistory;
    private boolean accept3PartyCookies;
	private boolean saveFormData;
	private boolean javaScriptEnabled;
	//private boolean pluginsEnabled;
	private boolean appCacheEnabled;
	private boolean allowContentAccess;
	private boolean mediaPlaybackRequiresUserGesture;
	private boolean loadWithOverviewMode;
	private boolean textReflow;
	private boolean domStorageEnabled;
	//private boolean enableSmoothTransition;
	private boolean geolocationEnabled;
	private int mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
	private boolean databaseEnabled;
	private boolean offscreenPreRaster;
	//private boolean savePassword;
	private String userAgentString;
	//private boolean supportMultipleWindows;
	//private boolean useWebViewBackgroundForOverscrollBackground;
	//private boolean navDump;
	//private boolean lightTouchEnabled;
	//private boolean useDoubleTree;
	private boolean allowFileAccess;
	private boolean allowFileAccessFromFileURLs;
	private boolean allowUniversalAccessFromFileURLs;
	private boolean blockNetworkLoads;
	private boolean javaScriptCanOpenWindowsAutomatically;
	private boolean blockFonts;
	private boolean blockCSS;
	private boolean blockJavaScript;
	private boolean autoHideToolbar;
	private int renderMode;
	private boolean removeIdentifyingHeaders;
	private String downloadLocation;
	private int textColor;
	private int backgroundColor;
	private String textEncoding;
	private String deleteAfter;
	
	private int cacheMode;
	private boolean isDesktopUA;
	private boolean requestSaveData;
	private boolean doNotTrack;
	private Paint paint = new Paint();
	
    private SQLiteDatabase placesDb;
	private WebView printWeb;
	private PrintJob printJob;
	
    private ValueCallback<Uri[]> fileUploadCallback;
    private boolean fileUploadCallbackShouldReset;
	
	boolean invertPage = false;
	//private WebViewHandler webViewHandler = new WebViewHandler(this);
    private int SCROLL_UP_THRESHOLD = 50;//dpToPx(10f);
	private float maxFling = 50;
	GestureDetector gestureDetector;// = new GestureDetector(this, new CustomGestureListener());
	
	private float[] negativeColorArray = new float[] {
		-1.0f, 0f, 0f, 0f, 255f, // red
		0f, -1.0f, 0f, 0f, 255f, // green
		0f, 0f, -1.0f, 0f, 255f, // blue
		0f, 0f, 0f, 1.0f, 0f // alpha
	};
	private float[] increaseContrastColorArray = new float[] {
		2.0f, 0f, 0f, 0f, -160f, // red
		0f, 2.0f, 0f, 0f, -160f, // green
		0f, 0f, 2.0f, 0f, -160f, // blue
		0f, 0f, 0f, 1.0f, 0f // alpha
	};
	
	private static class MenuAction {

        static HashMap<String, MenuAction> actions = new HashMap<>();

        private MenuAction(String title, int icon, Runnable action) {
            this(title, icon, action, null);
        }

        private MenuAction(String title, int icon, Runnable action, MyBooleanSupplier getState) {
            this.title = title;
            this.icon = icon;
            this.action = action;
            this.getState = getState;
            actions.put(title, this);
        }

        @Override
        public String toString() {
            return title;
        }

        private String title;
        private int icon;
        private Runnable action;
        private MyBooleanSupplier getState;
    }

    // java.util.function.BooleanSupplier requires API 24
    interface MyBooleanSupplier {
        boolean getAsBoolean();
    }
	
	@SuppressWarnings("unchecked")
    final MenuAction[] menuActions = new MenuAction[]{
		new MenuAction("Menu", R.drawable.menu, new Runnable() {
				@Override
				public void run() {
					showMenu();
				}
			}),
		new MenuAction("Full menu", R.drawable.menu, new Runnable() {
				@Override
				public void run() {
					isFullMenu = !isFullMenu;
					prefs.edit().putBoolean("isFullMenu", isFullMenu).apply();
					showMenu();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return isFullMenu;
				}
			}),
		new MenuAction("Default Encoding", 0, new Runnable() {
				@Override
				public void run() {
					encodingDialog(true);
				}
			}),
		new MenuAction("Save Page", R.drawable.ic_action_save, new Runnable() {
				@Override
				public void run() {
					final WebView currentWebView = getCurrentWebView();
					if (!currentWebView.getUrl().equals("about:blank")) {
						if (!hasOrRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
													null,
													PERMISSION_REQUEST_DOWNLOAD)) {
							return;
						}
						String url = savedName(currentWebView);
						String uniqueName = getUniqueName(downloadLocation, url, ".mht");
						currentWebView.saveWebArchive(uniqueName);
						AndroidUtils.toast(MainActivity.this, "Saved " + uniqueName);
					}
				}
			}),

		new MenuAction("Save Form Data", 0, new Runnable() {
				@Override
				public void run() {
					saveFormData = !saveFormData;
					prefs.edit().putBoolean("saveFormData", saveFormData).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setSaveFormData(saveFormData);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return saveFormData;
				}
			}),
		new MenuAction("Find on page", R.drawable.find_on_page, new Runnable() {
				@Override
				public void run() {
					searchEdit.setText("");
					searchPane.setVisibility(View.VISIBLE);
					searchEdit.requestFocus();
					showKeyboard();
				}
			}),

		new MenuAction("Desktop UA", R.drawable.ua, new Runnable() {
				@Override
				public void run() {
					isDesktopUA = !isDesktopUA;
					prefs.edit().putBoolean("isDesktopUA", isDesktopUA).apply();
					for (Tab t : tabs) {
						WebSettings settings = t.webview.getSettings();
						settings.setUserAgentString(isDesktopUA ? desktopUA : androidUA);
						settings.setUseWideViewPort(isDesktopUA);
						t.webview.reload();
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return isDesktopUA;
				}
			}),
		new MenuAction("Enable Cookies", R.drawable.cookies, new Runnable() {
				@Override
				public void run() {
					enableCookies = !enableCookies;
					prefs.edit().putBoolean("enableCookies", enableCookies).apply();
					CookieManager.getInstance().setAcceptCookie(enableCookies);
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return CookieManager.getInstance().acceptCookie();
				}
			}),
		new MenuAction("3rd party cookies", R.drawable.cookies_3rdparty, new Runnable() {
				@Override
				public void run() {
					accept3PartyCookies = !accept3PartyCookies;
					prefs.edit().putBoolean("accept3PartyCookies", accept3PartyCookies).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setAcceptThirdPartyCookies(accept3PartyCookies);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return accept3PartyCookies;
				}
			}),

		new MenuAction("Do Not Track", 0, new Runnable() {
				@Override
				public void run() {
					doNotTrack = !doNotTrack;
					prefs.edit().putBoolean("doNotTrack", doNotTrack).apply();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return doNotTrack;
				}
			}),
		new MenuAction("Request 'Save-Data'", 0, new Runnable() {
				@Override
				public void run() {
					requestSaveData = !requestSaveData;
					prefs.edit().putBoolean("requestSaveData", requestSaveData).apply();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return requestSaveData;
				}
			}),
		new MenuAction("Remove Identifying Headers", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					removeIdentifyingHeaders = !removeIdentifyingHeaders;
					prefs.edit().putBoolean("removeIdentifyingHeaders", removeIdentifyingHeaders).apply();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return removeIdentifyingHeaders;
				}
			}),
		new MenuAction("Block CSS", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					blockCSS = !blockCSS;
					prefs.edit().putBoolean("blockCSS", blockCSS).apply();
					for (Tab t : tabs) {
						t.blockCSS = blockCSS;
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockCSS;
				}
			}),
		new MenuAction("Block Fonts", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					blockFonts = !blockFonts;
					prefs.edit().putBoolean("blockFonts", blockFonts).apply();
					for (Tab t : tabs) {
						t.blockFonts = blockFonts;
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockFonts;
				}
			}),
		new MenuAction("Block Images", R.drawable.ic_doc_image, new Runnable() {
				@Override
				public void run() {
					blockImages = !blockImages;
					prefs.edit().putBoolean("blockImages", blockImages).apply();
					for (Tab t : tabs) {
						t.blockImages = blockImages;
						t.webview.getSettings().setBlockNetworkImage(blockImages);
						t.webview.getSettings().setLoadsImagesAutomatically(!blockImages);
					}
					if (blockImages) {
						AndroidUtils.toast(MainActivity.this, "Blocked Images");
						blockImagesImageView.setImageResource(R.drawable.adblocker);
					} else {
						AndroidUtils.toast(MainActivity.this, "Unblocked Images");
						blockImagesImageView.setImageResource(R.drawable.ic_doc_image);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockImages;
				}
			}),
		new MenuAction("Block Media", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					blockMedia = !blockMedia;
					prefs.edit().putBoolean("blockMedia", blockMedia).apply();
					for (Tab t : tabs) {
						t.blockMedia = blockMedia;
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockMedia;
				}
			}),
		new MenuAction("Block JavaScript", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					blockJavaScript = !blockJavaScript;
					prefs.edit().putBoolean("blockJavaScript", blockJavaScript).apply();
					for (Tab t : tabs) {
						t.blockJavaScript = blockJavaScript;
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockJavaScript;
				}
			}),
		new MenuAction("Block Network Loads", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					blockNetworkLoads = !blockNetworkLoads;
					prefs.edit().putBoolean("blockNetworkLoads", blockNetworkLoads).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setBlockNetworkLoads(blockNetworkLoads);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return blockNetworkLoads;
				}
			}),
		new MenuAction("Ad Blocker", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					useAdBlocker = !useAdBlocker;
					initAdblocker();
					prefs.edit().putBoolean("adblocker", useAdBlocker).apply();
					AndroidUtils.toast(MainActivity.this, "Ad Blocker " + (useAdBlocker ? "enabled" : "disabled"));
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return useAdBlocker;
				}
			}),
		new MenuAction("Add Block Rules", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					addBlockRules(null);
				}
			}),
		new MenuAction("Update adblock rules", R.drawable.adblocker, new Runnable() {
				@Override
				public void run() {
					updateAdblockRules();
				}
			}),
		
		new MenuAction("Database Enabled", 0, new Runnable() {
				@Override
				public void run() {
					databaseEnabled = !databaseEnabled;
					prefs.edit().putBoolean("databaseEnabled", databaseEnabled).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setDatabaseEnabled(databaseEnabled);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return databaseEnabled;
				}
			}),
		new MenuAction("App Cache Enabled", 0, new Runnable() {
				@Override
				public void run() {
					appCacheEnabled = !appCacheEnabled;
					prefs.edit().putBoolean("appCacheEnabled", appCacheEnabled).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setAppCacheEnabled(appCacheEnabled);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return appCacheEnabled;
				}
			}),
		new MenuAction("DomStorage Enabled", 0, new Runnable() {
				@Override
				public void run() {
					domStorageEnabled = !domStorageEnabled;
					prefs.edit().putBoolean("domStorageEnabled", domStorageEnabled).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setDomStorageEnabled(domStorageEnabled);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return domStorageEnabled;
				}
			}),
		new MenuAction("Geolocation Enabled", 0, new Runnable() {
				@Override
				public void run() {
					geolocationEnabled = !geolocationEnabled;
					prefs.edit().putBoolean("geolocationEnabled", geolocationEnabled).apply();
					for (Tab t : tabs) {
						if (!t.isIncognito)
							t.webview.getSettings().setGeolocationEnabled(geolocationEnabled);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return geolocationEnabled;
				}
			}),
		new MenuAction("JavaScript Enabled", 0, new Runnable() {
				@Override
				public void run() {
					javaScriptEnabled = !javaScriptEnabled;
					prefs.edit().putBoolean("javaScriptEnabled", javaScriptEnabled).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setJavaScriptEnabled(javaScriptEnabled);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return javaScriptEnabled;
				}
			}),
		new MenuAction("Popup Windows", 0, new Runnable() {
				@Override
				public void run() {
					javaScriptCanOpenWindowsAutomatically = !javaScriptCanOpenWindowsAutomatically;
					prefs.edit().putBoolean("javaScriptCanOpenWindowsAutomatically", javaScriptCanOpenWindowsAutomatically).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(javaScriptCanOpenWindowsAutomatically);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return javaScriptCanOpenWindowsAutomatically;
				}
			}),
		new MenuAction("Auto Hide Toolbar", 0, new Runnable() {
				@Override
				public void run() {
					autoHideToolbar = !autoHideToolbar;
					prefs.edit().putBoolean("autoHideToolbar", autoHideToolbar).apply();
					if (autoHideToolbar) {
						for (Tab t : tabs) {
							t.webview.setOnTouchListener(new TouchListener());
						}
					} else {
						for (Tab t : tabs) {
							t.webview.setOnTouchListener(null);
						}
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return autoHideToolbar;
				}
			}),
		new MenuAction("Download Location", 0, new Runnable() {
				@Override
				public void run() {
					final ArrayList<MenuAction> actions = new ArrayList<>(2);
					final MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
						MainActivity.this,
						android.R.layout.simple_list_item_1,
						actions);
					actions.add(new MenuAction("Default", 0, new Runnable() {
										@Override
										public void run() {
											downloadLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return downloadLocation.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
										}
									}));
					actions.add(new MenuAction("Custom", 0, new Runnable() {
										@Override
										public void run() {
											final EditText editView = new EditText(MainActivity.this);
											editView.setText(downloadLocation);
											editView.setSelection(downloadLocation.length());
											new AlertDialog.Builder(MainActivity.this)
												.setTitle("Edit Download Location")
												.setView(editView)
												.setPositiveButton("Apply", new OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														String toString = editView.getText().toString().replaceAll("/{2,}", "/");
														if (toString.endsWith("/")) {
															toString = toString.substring(0, toString.lastIndexOf("/"));
														}
														File file = new File(toString);
														if (file.exists() && file.isDirectory() && file.canWrite()) {
															downloadLocation = toString;
															prefs.edit().putString("downloadLocation", downloadLocation).apply();
															adapter.notifyDataSetChanged();
														}
													}})
												.setNegativeButton("Cancel", new EmptyOnClickListener())
												.show();
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return !downloadLocation.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
										}
									}));
					AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.setTitle("Download Location")
						.create();
					ListView tv = new ListView(MainActivity.this);
					tv.setAdapter(adapter);
					dialog.setView(tv);
					dialog.show();
				}
			}),
		new MenuAction("Night mode", R.drawable.night, new Runnable() {
				@Override
				public void run() {
					isNightMode = !isNightMode;
					prefs.edit().putBoolean("night_mode", isNightMode).apply();
					onNightModeChange();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return isNightMode;
				}
			}),
		new MenuAction("Show address bar", R.drawable.url_bar, new Runnable() {
				@Override
				public void run() {
					et.setVisibility(et.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
					goStop.setVisibility(goStop.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return address.getVisibility() == View.VISIBLE;
				}
			}),
		new MenuAction("Full screen", R.drawable.fullscreen, new Runnable() {
				@Override
				public void run() {
					isFullscreen = !isFullscreen;
					updateFullScreen();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return isFullscreen;
				}
			}),
		new MenuAction("Tab history", R.drawable.left_right, new Runnable() {
				@Override
				public void run() {
					showTabHistory();
				}
			}),
		new MenuAction("Log requests", R.drawable.log_requests, new Runnable() {
				@Override
				public void run() {
					isLogRequests = !isLogRequests;
					prefs.edit().putBoolean("isLogRequests", isLogRequests).apply();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return isLogRequests;
				}
			}),
//		new MenuAction("Show Log Requests", R.drawable.log_requests, new Runnable() {
//				@Override
//				public void run() {
////					StringBuilder sb = new StringBuilder("<title>Request Log</title><h1>Request Log</h1>");
////					for (String url : getCurrentTab().requestsLog) {
////						sb.append("<a href=\"");
////						sb.append(url);
////						sb.append("\">");
////						sb.append(url);
////						sb.append("</a><br><br>");
////					}
////					String base64 = Base64.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
////					newBackgroundTab("data:text/html;base64," + base64, false);
////					switchToTab(tabs.size() - 1);
//					
//				}
//			}),
		new MenuAction("Media Playback Requires Gesture", 0, new Runnable() {
				@Override
				public void run() {
					mediaPlaybackRequiresUserGesture = !mediaPlaybackRequiresUserGesture;
					prefs.edit().putBoolean("mediaPlaybackRequiresUserGesture", mediaPlaybackRequiresUserGesture).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setMediaPlaybackRequiresUserGesture(mediaPlaybackRequiresUserGesture);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return mediaPlaybackRequiresUserGesture;
				}
			}),
		
		new MenuAction("Text Reflow", 0, new Runnable() {
				@Override
				public void run() {
					textReflow = !textReflow;
					prefs.edit().putBoolean("textReflow", textReflow).apply();
					Tab t = getCurrentTab();
					WebSettings settings = t.webview.getSettings();
					if (textReflow) {
						settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
						try {
							settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
						} catch (Exception e) {
							// This shouldn't be necessary, but there are a number
							// of KitKat devices that crash trying to set this
							ExceptionLogger.e("Problem setting LayoutAlgorithm to TEXT_AUTOSIZING", e.getMessage());
						}
					} else {
						settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return textReflow;
				}
			}),
		new MenuAction("Offscreen PreRaster", 0, new Runnable() {
				@Override
				public void run() {
					offscreenPreRaster = !offscreenPreRaster;
					prefs.edit().putBoolean("offscreenPreRaster", offscreenPreRaster).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setOffscreenPreRaster(offscreenPreRaster);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return offscreenPreRaster;
				}
			}),
		new MenuAction("Load With Overview Mode", 0, new Runnable() {
				@Override
				public void run() {
					loadWithOverviewMode = !loadWithOverviewMode;
					prefs.edit().putBoolean("loadWithOverviewMode", loadWithOverviewMode).apply();
					for (Tab t : tabs) {
						t.webview.getSettings().setLoadWithOverviewMode(loadWithOverviewMode);
					}
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return loadWithOverviewMode;
				}
			}),
		new MenuAction("Access Mode", 0, new Runnable() {
				@Override
				public void run() {
					ArrayList<MenuAction> actions = new ArrayList<>(5);
					actions.add(new MenuAction("Allow File Access", 0, new Runnable() {
										@Override
										public void run() {
											allowFileAccess = !allowFileAccess;
											prefs.edit().putBoolean("allowFileAccess", allowFileAccess).apply();
											for (Tab t : tabs) {
												t.webview.getSettings().setAllowFileAccess(allowFileAccess);
											}
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return allowFileAccess;
										}
									}));
					actions.add(new MenuAction("Allow Content Access", 0, new Runnable() {
										@Override
										public void run() {
											allowContentAccess = !allowContentAccess;
											prefs.edit().putBoolean("allowContentAccess", allowContentAccess).apply();
											for (Tab t : tabs) {
												t.webview.getSettings().setAllowContentAccess(allowContentAccess);
											}
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return allowContentAccess;
										}
									}));
					actions.add(new MenuAction("Allow File Access From File URLs", 0, new Runnable() {
										@Override
										public void run() {
											allowFileAccessFromFileURLs = !allowFileAccessFromFileURLs;
											prefs.edit().putBoolean("allowFileAccessFromFileURLs", allowFileAccessFromFileURLs).apply();
											for (Tab t : tabs) {
												t.webview.getSettings().setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs);
											}
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return allowFileAccessFromFileURLs;
										}
									}));
					actions.add(new MenuAction("Allow Universal Access From File URLs", 0, new Runnable() {
										@Override
										public void run() {
											allowUniversalAccessFromFileURLs = !allowUniversalAccessFromFileURLs;
											prefs.edit().putBoolean("allowUniversalAccessFromFileURLs", allowUniversalAccessFromFileURLs).apply();
											for (Tab t : tabs) {
												t.webview.getSettings().setAllowUniversalAccessFromFileURLs(allowUniversalAccessFromFileURLs);
											}
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return allowUniversalAccessFromFileURLs;
										}
									}));
					
					AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.setTitle("Access Mode")
						.create();
					ListView tv = new ListView(MainActivity.this);
					MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
						MainActivity.this,
						android.R.layout.simple_list_item_1,
						actions);
					tv.setAdapter(adapter);
					dialog.setView(tv);
					dialog.show();
				}
			}),
		new MenuAction("Render Mode", 0, new Runnable() {
				@Override
				public void run() {
					ArrayList<MenuAction> actions = new ArrayList<>(5);
					actions.add(new MenuAction("NORMAL", 0, new Runnable() {
										@Override
										public void run() {
											renderMode(0);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return renderMode == 0;
										}
									}));
					actions.add(new MenuAction("INVERTED", 0, new Runnable() {
										@Override
										public void run() {
											renderMode(1);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return renderMode == 1;
										}
									}));
					actions.add(new MenuAction("GRAYSCALE", 0, new Runnable() {
										@Override
										public void run() {
											renderMode(2);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return renderMode == 2;
										}
									}));
					actions.add(new MenuAction("INVERTED GRAYSCALE", 0, new Runnable() {
										@Override
										public void run() {
											renderMode(3);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return renderMode == 3;
										}
									}));
					actions.add(new MenuAction("INCREASE CONTRAST", 0, new Runnable() {
										@Override
										public void run() {
											renderMode(4);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return renderMode == 4;
										}
									}));

					AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.setTitle("Render Mode")
						.create();
					ListView tv = new ListView(MainActivity.this);
					MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
						MainActivity.this,
						android.R.layout.simple_list_item_1,
						actions);
					tv.setAdapter(adapter);
					dialog.setView(tv);
					dialog.show();
				}
			}),
		new MenuAction("Cache Mode", 0, new Runnable() {
				@Override
				public void run() {
					ArrayList<MenuAction> actions = new ArrayList<>(4);
					actions.add(new MenuAction("LOAD DEFAULT", 0, new Runnable() {
										@Override
										public void run() {
											cacheMode(WebSettings.LOAD_DEFAULT);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return cacheMode == WebSettings.LOAD_DEFAULT;
										}
									}));
					actions.add(new MenuAction("LOAD CACHE ELSE NETWORK", 0, new Runnable() {
										@Override
										public void run() {
											cacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return cacheMode == WebSettings.LOAD_CACHE_ELSE_NETWORK;
										}
									}));
					actions.add(new MenuAction("LOAD NO CACHE", 0, new Runnable() {
										@Override
										public void run() {
											cacheMode(WebSettings.LOAD_NO_CACHE);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return cacheMode == WebSettings.LOAD_NO_CACHE;
										}
									}));
					actions.add(new MenuAction("LOAD CACHE ONLY", 0, new Runnable() {
										@Override
										public void run() {
											cacheMode(WebSettings.LOAD_CACHE_ONLY);
										}
									}, new MyBooleanSupplier() {
										@Override
										public boolean getAsBoolean() {
											return cacheMode == WebSettings.LOAD_CACHE_ONLY;
										}
									}));

					AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						})
						.setTitle("Cache Mode")
						.create();
					ListView tv = new ListView(MainActivity.this);
					MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
						MainActivity.this,
						android.R.layout.simple_list_item_1,
						actions);
					tv.setAdapter(adapter);
					dialog.setView(tv);
					dialog.show();
				}
			}),
		
		new MenuAction("Back", R.drawable.back, new Runnable() {
				@Override
				public void run() {
					if (getCurrentWebView().canGoBack())
						getCurrentWebView().goBack();
				}
			}),
		new MenuAction("Forward", R.drawable.forward, new Runnable() {
				@Override
				public void run() {
					if (getCurrentWebView().canGoForward())
						getCurrentWebView().goForward();
				}
			}),
		new MenuAction("Scroll to top", R.drawable.top, new Runnable() {
				@Override
				public void run() {
					getCurrentWebView().pageUp(true);
				}
			}),
		new MenuAction("Scroll to bottom", R.drawable.bottom, new Runnable() {
				@Override
				public void run() {
					getCurrentWebView().pageDown(true);
				}
			}),

		new MenuAction("Keep History", R.drawable.ic_history_black_36dp, new Runnable() {
				@Override
				public void run() {
					saveHistory = !saveHistory;
					prefs.edit().putBoolean("saveHistory", saveHistory).apply();
				}
			}, new MyBooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return saveHistory;
				}
			}),
		new MenuAction("Delete all history", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					deleteAllHistory();
				}
			}),
		new MenuAction("Delete History After", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					final EditText editView = new EditText(MainActivity.this);
					editView.setText(deleteAfter);
					editView.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
					editView.setSelection(deleteAfter.length());
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("Delete History After Days")
						.setView(editView)
						.setPositiveButton("Apply", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								deleteAfter = editView.getText().toString();
								prefs.edit().putString("deleteAfter", deleteAfter).apply();
								if (placesDb != null) 
									placesDb.execSQL("DELETE FROM history WHERE date_created < DATETIME('now', '-" + deleteAfter + " day')", new Object[] {});
							}})
						.setNegativeButton("Cancel", new EmptyOnClickListener())
						.show();
				}
			}),
		new MenuAction("Show History", R.drawable.ic_history_black_36dp, new Runnable() {
				@Override
				public void run() {
					showHistory();
				}
			}),
		new MenuAction("Show Bookmarks", R.drawable.bookmarks, new Runnable() {
				@Override
				public void run() {
					showBookmarks();
				}
			}),
		new MenuAction("Add bookmark", R.drawable.bookmark_add, new Runnable() {
				@Override
				public void run() {
					addBookmark();
				}
			}),
		new MenuAction("Export bookmarks", R.drawable.bookmarks_export, new Runnable() {
				@Override
				public void run() {
					exportBookmarks();
				}
			}),
		new MenuAction("Import bookmarks", R.drawable.bookmarks_import, new Runnable() {
				@Override
				public void run() {
					importBookmarks();
				}
			}),
		new MenuAction("Delete all bookmarks", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					deleteAllBookmarks();
				}
			}),
		new MenuAction("Delete all debug logs", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					final File[] fs = externalLogFilesDir.listFiles();
					if (fs != null) {
						for (File f : fs) {
							if (!f.equals(ExceptionLogger.file))
								f.delete();
						}
						AndroidUtils.toast(MainActivity.this, "Finished delete all logs");
					}
				}
			}),
		
		new MenuAction("Clear history and cache", R.drawable.ic_delete_white_36dp, new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("Clear history and cache")
						.setItems(new String[] {"Clear history", "Clear cookies", "Clear cache", "Clear all"}, new OnClickListener() {
							public void onClick(DialogInterface subDialog, int which) {
								final WebView v = getCurrentWebView();
								switch (which) {
									case 0: {
										v.clearHistory();
										break;
										}
									case 1: {
										CookieManager.getInstance().removeAllCookies(null);
										break;
										}
									case 2:
										v.clearCache(true);
										v.clearFormData();
										break;
									case 3:
										v.clearCache(true);
										v.clearFormData();
										v.clearHistory();
										CookieManager.getInstance().removeAllCookies(null);
										WebStorage.getInstance().deleteAllData();
										break;
								}
							}})
						.show();
				}
			}),

		new MenuAction("Show tabs", R.drawable.tabs, new Runnable() {
				@Override
				public void run() {
					showOpenTabs();
				}
			}),
		new MenuAction("New tab", R.drawable.tab_new, new Runnable() {
				@Override
				public void run() {
					newBackgroundTab("", false);
					switchToTab(tabs.size() - 1);
				}
			}),
		new MenuAction("Close tab", R.drawable.tab_close, new Runnable() {
				@Override
				public void run() {
					if (tabs.size() > 1) {
						closeCurrentTab();
					} else {
						newBackgroundTab("about:blank", false);
						closeCurrentTab();
					}
				}
			}),
	};

	private void encodingDialog(final boolean all) {
		final ArrayList<MenuAction> actions = new ArrayList<>(8);
		textEncoding(actions, all);

		final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.setTitle("Default Encoding")
			.create();
		final ListView tv = new ListView(MainActivity.this);
		final MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
			MainActivity.this,
			android.R.layout.simple_list_item_1,
			actions);
		tv.setAdapter(adapter);
		dialog.setView(tv);
		dialog.show();
	}

	private void textEncoding(final ArrayList<MenuAction> actions, final boolean all) {
		createEncodingMenu(actions, "ISO-8859-1", all);
		createEncodingMenu(actions, "UTF-8", all);
		createEncodingMenu(actions, "GBK", all);
		createEncodingMenu(actions, "Big5", all);
		createEncodingMenu(actions, "ISO-2022-JP", all);
		createEncodingMenu(actions, "SHIFT_JS", all);
		createEncodingMenu(actions, "EUC-JP", all);
		createEncodingMenu(actions, "EUC-KR", all);
	}

	private void createEncodingMenu(final ArrayList<MenuAction> actions, final String encode, final boolean all) {
		actions.add(new MenuAction(encode, 0, new Runnable() {
							@Override
							public void run() {
								if (all) {
									textEncoding = encode;
									prefs.edit().putString("textEncoding", textEncoding).apply();
									for (Tab t : tabs) {
										t.webview.getSettings().setDefaultTextEncodingName(textEncoding);
									}
								} else {
									getCurrentWebView().getSettings().setDefaultTextEncodingName(encode);
								}
							}
						}, new MyBooleanSupplier() {
							@Override
							public boolean getAsBoolean() {
								if (all)
									return encode.equals(textEncoding);
								else
									return encode.equals(getCurrentWebView().getSettings().getDefaultTextEncodingName());
							}
						}));
	}

	private void cacheMode(final int mode) {
		cacheMode = mode;
		prefs.edit().putInt("cacheMode", cacheMode).apply();
		for (Tab t : tabs) {
			if (!t.isIncognito)
				t.webview.getSettings().setCacheMode(cacheMode);
		}
	}

	private void renderMode(final int which) {
		renderMode = which;
		prefs.edit().putInt("renderMode", renderMode).apply();
		for (Tab t : tabs) {
			setRenderMode(t.webview, which);
		}
	}
	
    final String[][] toolbarActions = {
		{"Back", "Scroll to top", "Tab history"},
		{"Forward", "Scroll to bottom", "Block Images"},
		{"Show Bookmarks", "Show History", "Add bookmark"},
		{"Save Page", "Night mode", "Full screen"},
		{"Show tabs", "New tab", "Close tab"},
		{"Menu", "Find on page", "Show address bar"},
    };

    final String[] shortMenu = {
		"Full menu", "New tab", "Desktop UA", "Show History", "Tab history", "Show address bar", 
		"Find on page", "Block Images", "Add bookmark", "Full screen", "Close tab"
    };

    MenuAction getAction(String name) {
        MenuAction action = MenuAction.actions.get(name);
        if (action == null) throw new IllegalArgumentException("name");
        return action;
    }

    static class TitleAndUrl {
        String title;
        String url;
    }

    static class TitleAndBundle {
        String title;
        Bundle bundle;
		boolean isIncognito;
    }

    private ArrayList<TitleAndBundle> closedTabs = new ArrayList<>();

    private Tab getCurrentTab() {
        return tabs.get(currentTabIndex);
    }

    private WebView getCurrentWebView() {
        return getCurrentTab().webview;
    }
	
	@JavascriptInterface
	public void showSource(final String tabId, final String html) {
		for (Tab t : tabs) {
			if (t.toString().equals(tabId)) {
				t.source = html;
			}
		}
	}
	
	@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
    private WebView createWebView(final Bundle bundle) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
		final WebView webview;
		webview = new WebView(this);
		if (bundle != null) {
            webview.restoreState(bundle);
        }
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setBackgroundColor(isNightMode ? DARK_BACKGROUND : LIGHT_BACKGROUND);
        final WebSettings settings = webview.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webview.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(final WebView view, final int newProgress) {
					super.onProgressChanged(view, newProgress);
					//injectCSS(view);
					if (newProgress == 100) {
						progressBar.setVisibility(View.GONE);
					} else {
						progressBar.setProgress(newProgress);
					}
				}

				@Override
				public void onShowCustomView(final View view, final CustomViewCallback callback) {
					fullScreenView[0] = view;
					fullScreenCallback[0] = callback;
					MainActivity.this.findViewById(R.id.main_layout).setVisibility(View.INVISIBLE);
					ViewGroup fullscreenLayout = (ViewGroup) MainActivity.this.findViewById(R.id.fullScreenVideo);
					fullscreenLayout.addView(view);
					fullscreenLayout.setVisibility(View.VISIBLE);
				}

				@Override
				public void onHideCustomView() {
					if (fullScreenView[0] == null)
						return;
					final ViewGroup fullscreenLayout = (ViewGroup) MainActivity.this.findViewById(R.id.fullScreenVideo);
					fullscreenLayout.removeView(fullScreenView[0]);
					fullscreenLayout.setVisibility(View.GONE);
					fullScreenView[0] = null;
					fullScreenCallback[0] = null;
					MainActivity.this.findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
				}

				@Override
				public boolean onShowFileChooser(final WebView webView, final ValueCallback<Uri[]> filePathCallback, final FileChooserParams fileChooserParams) {
					if (!hasOrRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
												null,
												0)) {
						AndroidUtils.toast(MainActivity.this, "No permission to read file");
						fileUploadCallback = null;
						return false;
					}
					if (fileUploadCallback != null) {
						fileUploadCallback.onReceiveValue(null);
					}

					fileUploadCallback = filePathCallback;
					final Intent intent = fileChooserParams.createIntent();
					try {
						fileUploadCallbackShouldReset = true;
						startActivityForResult(intent, FORM_FILE_CHOOSER);
						return true;
					} catch (ActivityNotFoundException e) {
						// Continue below
					}

					// FileChooserParams.createIntent() copies the <input type=file> "accept" attribute to the intent's getType(),
					// which can be e.g. ".png,.jpg" in addition to mime-type-style "image/*", however startActivityForResult()
					// only accepts mime-type-style. Try with just */* instead.
					intent.setType("*/*");
					try {
						fileUploadCallbackShouldReset = false;
						startActivityForResult(intent, FORM_FILE_CHOOSER);
						return true;
					} catch (ActivityNotFoundException e) {
						// Continue below
					}

					// Everything failed, let user know
					AndroidUtils.toast(MainActivity.this, "Can't open file chooser");
					fileUploadCallback = null;
					return false;
				}
			});
//        String[] imgs = returnImageUrlsFromHtml(htmlData);
//		webview.addJavascriptInterface(new ImageJavascriptInterface(MainActivity.this, imgs), "click");
		webview.addJavascriptInterface(MainActivity.this, "HTMLOUT");
		webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					//ExceptionLogger.d(TAG, "onPageStarted " + url + ", favicon " + favicon);
					progressBar.setProgress(0);
					progressBar.setVisibility(View.VISIBLE);
					
					if (view.getVisibility() == View.VISIBLE) {
						et.setText(url);
						et.setSelection(0);
						view.requestFocus();
					}
					printWeb = null;
					goStop.setImageResource(R.drawable.stop);
					Tab currentTab = null;
					for(Tab t : tabs) {
						if (t.webview == view) {
							currentTab = t;
							break;
						}
					}
					currentTab.favHref = url;
					if (favicon == null) {
						faviconImage.setImageResource(R.drawable.page_info);
						faviconImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
						faviconImage.setBackgroundColor(backgroundColor);
						currentTab.favicon = null;
					} else {
						faviconImage.clearColorFilter();
						faviconImage.setImageBitmap(favicon);
						currentTab.favicon = favicon;
					}
					currentTab.loading = true;
					currentTab.textChanged = false;
					//injectCSS(view);
				}

				@Override
				public void onPageFinished(final WebView view, final String url) {
					super.onPageFinished(view, url);
					//ExceptionLogger.d(TAG, "onPageFinished " + url + ", getFavicon " + view.getFavicon());
					printWeb = view;
					if (view == getCurrentWebView()) {
						// Don't use the argument url here since navigation to that URL might have been
						// cancelled due to SSL error
						goStop.setImageResource(R.drawable.reload);
						final Tab currentTab = getCurrentTab();
						if (!currentTab.textChanged) {
							currentTab.skipTextChange = true;
							et.setText(currentTab.webview.getTitle());
							currentTab.skipTextChange = false;
						}
						if (et.getSelectionStart() == 0 && et.getSelectionEnd() == 0 && et.getText().toString().equals(view.getUrl())) {
							// If user haven't started typing anything, focus on webview
							view.requestFocus();
						}
					}
//					String jsCode = "javascript:(function(){" +
//						"var imgs=document.getElementsByTagName(\"img\");" +
//						"for(var i=0;i<imgs.length;i++){" +
//						"imgs[i].pos = i;"+
//						"imgs[i].οnclick=function(){" +
//						"click.openImage(this.src,this.pos);" +
//						"}}})()";
//                    view.loadUrl(jsCode);
					Tab currentTab = null;
					for(Tab t : tabs) {
						if (t.webview == view) {
							currentTab = t;
							break;
						}
					}
					if (saveHistory 
						&& !currentTab.isIncognito
						&& !url.startsWith("data")
						&& !url.equals("about:blank")) {
						addHistory(view, url);
					}
					//ExceptionLogger.d(TAG, "javascript:window.HTMLOUT.showSource(" + currentTab.toString() + ", document.documentElement.outerHTML)" + ", tabId.toString() " + currentTab.toString());
					view.loadUrl("javascript:window.HTMLOUT.showSource(\"" + currentTab.toString() + "\", document.documentElement.outerHTML)");
					final Bitmap favicon = view.getFavicon();
					if (favicon != null) {
						faviconImage.clearColorFilter();
						faviconImage.setImageBitmap(favicon);
						currentTab.favicon = favicon;
					} else {
						faviconImage.setImageResource(R.drawable.page_info);
						faviconImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
						faviconImage.setBackgroundColor(backgroundColor);
						currentTab.favicon = null;
					}
					currentTab.favHref = url;
					currentTab.loading = false;
					if (requestList.getVisibility() == View.VISIBLE
						&& view.getVisibility() == View.VISIBLE) {
						log("");
					}
					//injectCSS(view);
				}

				@Override
				public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, String host, String realm) {
					new AlertDialog.Builder(MainActivity.this)
                        .setTitle(host)
                        .setView(R.layout.sweb_login_password)
                        .setCancelable(false)
						.setPositiveButton("OK", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								final String username = ((EditText) ((Dialog) dialog).findViewById(R.id.username)).getText().toString();
								final String password = ((EditText) ((Dialog) dialog).findViewById(R.id.password)).getText().toString();
								handler.proceed(username, password);
							}})
						.setNegativeButton("Cancel", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								handler.cancel();}}).show();
				}

				final InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);

				String lastMainPage = "";
				@Override
				public WebResourceResponse shouldInterceptRequest(final WebView view, final WebResourceRequest request) {
					final Uri url = request.getUrl();
					if (adBlocker != null) {
						if (request.isForMainFrame()) {
							lastMainPage = url.toString();
						}
						if (adBlocker.shouldBlock(url, lastMainPage)) {
							return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
						}
					}
					final String path = url.getPath();
					//ExceptionLogger.d(TAG, "path = " + path + ", view.getUrl() " + view.getUrl());
					if (path != null && !request.isForMainFrame()) {
						Tab currentTab = null;
						for(Tab t : tabs) {
							if (t.webview == view) {
								currentTab = t;
								break;
							}
						}
						if (FAVICON_PATTERN.matcher(path).matches()) {
							//ExceptionLogger.d(TAG, "shouldInterceptRequest.uri " + url.toString());
							if (currentTab.favicon == null && !url.getScheme().equals("file") &&
								url.getHost().equals(URI.create(currentTab.favHref).getHost())) {
//								ExceptionLogger.d(TAG, "shouldInterceptRequest.currentTab.favHref " + currentTab.favHref);
								new DownloadImageTask(faviconImage, currentTab)
									.execute(url.toString());
							}
						} else {
							final String scheme = url.getScheme();
							if (scheme.startsWith("http")
								|| scheme.startsWith("ftp")) {
								if (currentTab.blockImages && IMAGES_PATTERN.matcher(path).matches()) {
									return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
								} else if (currentTab.blockMedia && MEDIA_PATTERN.matcher(path).matches()) {
									return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
								} else if (currentTab.blockCSS && CSS_PATTERN.matcher(path).matches()) {
									return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
								} else if (currentTab.blockJavaScript && JAVASCRIPT_PATTERN.matcher(path).matches()) {
									return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
								} else if (currentTab.blockFonts && FONT_PATTERN.matcher(path).matches()) {
									return new WebResourceResponse("text/plain", "UTF-8", emptyInputStream);
								}
							}
						}
						final Map<String, String> requestHeaders = request.getRequestHeaders();
						if (requestSaveData) {
							requestHeaders.put("Save-Data", "on");
						} else {
							requestHeaders.remove("Save-Data");
						}
						if (doNotTrack) {
							requestHeaders.put("DNT", "1");
						} else {
							requestHeaders.remove("DNT");
						}
						if (removeIdentifyingHeaders) {
							requestHeaders.put("X-Requested-With", "");
							requestHeaders.put("X-Wap-Profile", "");
						} else {
							requestHeaders.remove("X-Requested-With");
							requestHeaders.remove("X-Wap-Profile");
						}
					}
					return super.shouldInterceptRequest(view, request);
				}

				@Override
				public boolean shouldOverrideUrlLoading(final WebView view, String url) {
					// For intent:// URLs, redirect to browser_fallback_url if given
					if (url.startsWith("intent://")) {
						int start = url.indexOf(";S.browser_fallback_url=");
						if (start != -1) {
							start += ";S.browser_fallback_url=".length();
							int end = url.indexOf(';', start);
							if (end != -1 && end != start) {
								url = url.substring(start, end);
								url = Uri.decode(url);
								loadUrl(url, view);
								return true;
							}
						}
					}
					return false;
				}
				
				@Override
				public void onLoadResource(final WebView view, final String url) {
					Tab currentTab = null;
					for(Tab t : tabs) {
						if (t.webview == view) {
							currentTab = t;
							break;
						}
					}
					if (isLogRequests) {
						currentTab.requestsLog.add(url);
					}
					if (currentTab.saveMedia
						&& currentTab.includePattern.matcher(url).matches()
						&& !currentTab.excludePattern.matcher(url).matches()) {
						currentTab.mediaList.add(new Article(url, null));
					}
				}

				final String[] sslErrors = {"Not yet valid", "Expired", "Hostname mismatch", "Untrusted CA", "Invalid date", "Unknown error"};

				@Override
				public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
					final int primaryError = error.getPrimaryError();
					final String errorStr = primaryError >= 0 && primaryError < sslErrors.length ? sslErrors[primaryError] : "Unknown error " + primaryError;
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("Insecure connection")
						.setMessage(String.format("Error: %s\nURL: %s\n\nCertificate:\n%s",
												  errorStr, error.getUrl(), AndroidUtils.certificateToStr(error.getCertificate())))
						.setPositiveButton("Proceed", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								handler.proceed();}})
						.setNegativeButton("Cancel", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								handler.cancel();}})
						.show();
				}
        });
        webview.setOnLongClickListener(new View.OnLongClickListener() {
				public boolean onLongClick(android.view.View v) {
					String url = null, imageUrl = null, text = "";
					final WebView.HitTestResult r = ((WebView) v).getHitTestResult();
					switch (r.getType()) {
						case WebView.HitTestResult.SRC_ANCHOR_TYPE:
							url = r.getExtra();
							break;
						case WebView.HitTestResult.IMAGE_TYPE: {
								imageUrl = r.getExtra();
								final Handler handler = new Handler();
								final Message message = handler.obtainMessage();
								((WebView)v).requestFocusNodeHref(message);
								Bundle bundle = message.getData();
								text = bundle.getString("title");
								if (text == null || text.length() == 0) {
									text = bundle.getString("alt");
									if (text == null)
										text = "";
								}
//								ExceptionLogger.d(TAG, "IMAGE_TYPE.bundle: " + bundle + ", VALUE.size: " + bundle.size());
//								for (String key : bundle.keySet()) {
//									ExceptionLogger.d(TAG, "IMAGE_TYPE.KEY: " + key + ", VALUE: " + bundle.get(key));
//								}
								break;
							}
						case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
						case WebView.HitTestResult.EMAIL_TYPE:
						case WebView.HitTestResult.UNKNOWN_TYPE:
							final Handler handler = new Handler();
							final Message message = handler.obtainMessage();
							((WebView)v).requestFocusNodeHref(message);
							Bundle bundle = message.getData();
							url = bundle.getString("url");
							if ("".equals(url)) {
								url = null;
							}
							imageUrl = bundle.getString("src");
							if ("".equals(imageUrl)) {
								imageUrl = null;
							}
							if (url == null && imageUrl == null) {
								return false;
							}
							text = bundle.getString("title");
							if (text == null || text.length() == 0) {
								text = bundle.getString("alt");
								if (text == null)
									text = "";
							}
//							ExceptionLogger.d(TAG, "bundle: " + bundle + ", VALUE: " + bundle.size());
//							for (String key : bundle.keySet()) {
//								ExceptionLogger.d(TAG, "KEY: " + key + ", VALUE: " + bundle.get(key));
//							}
							break;
						default:
							return false;
					}
					showLongPressMenu(url, imageUrl, text);
					return true;
				}});
        webview.setDownloadListener(new DownloadListener() {
				public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimetype, final long contentLength) {
					ExceptionLogger.d("onDownloadStart", url + ", userAgent " + userAgent + ", contentDisposition" + contentDisposition +", contentLength " + contentLength + ", mimetype " + mimetype);
					final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("Download")
						.setMessage(String.format("Filename: %s\nSize: %.2f MB\nURL: %s",
												  filename,
												  contentLength / 1024.0 / 1024.0,
												  url))
						.setPositiveButton("Download", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								startDownload(url, filename);}})
						.setNeutralButton("Open", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								Intent i = new Intent(Intent.ACTION_VIEW);
								i.setData(Uri.parse(url));
								try {
									startActivity(i);
								} catch (ActivityNotFoundException e) {
									new AlertDialog.Builder(MainActivity.this)
										.setTitle("Open")
										.setMessage("Can't open files of this type. Try downloading instead.")
										.setPositiveButton("OK", new EmptyOnClickListener())
										.show();
								}
							}})
						.setNegativeButton("Cancel", new EmptyOnClickListener())
						.show();
				}});
        webview.setFindListener(new WebView.FindListener() {
				public void onFindResultReceived(final int activeMatchOrdinal, final int numberOfMatches, final boolean isDoneCounting) {
					searchCount.setText(numberOfMatches == 0 ? "Not found" :
										String.format("%d / %d", activeMatchOrdinal + 1, numberOfMatches));
				}});
        return webview;
    }
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		final ImageView bmImage;
		final Tab tab;
		public DownloadImageTask(final ImageView bmImage, final Tab t) {
			this.bmImage = bmImage;
			this.tab = t;
		}
		protected Bitmap doInBackground(final String... urls) {
			final String urldisplay = urls[0];
			ExceptionLogger.d(TAG, "download favicon " + urldisplay);
			Bitmap mIcon11 = null;
			if (urldisplay != null) {
				try {
					final InputStream in = new java.net.URL(urldisplay).openStream();
					mIcon11 = BitmapFactory.decodeStream(in);
					in.close();
				} catch (Exception e) {
					ExceptionLogger.e("Error", e.getMessage());
				}
			}
			return mIcon11;
		}
		protected void onPostExecute(final Bitmap result) {
			if (result != null) {
				bmImage.clearColorFilter();
				bmImage.setImageBitmap(result);
				tab.favicon = result;
			} else {
				faviconImage.setImageResource(R.drawable.page_info);
				bmImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
				bmImage.setBackgroundColor(backgroundColor);
				tab.favicon = null;
				//tab.favHref = null;
			}
		}
	}
    void showLongPressMenu(final String linkUrl, final String imageUrl, final String text) {
        final String url;
        final String title;
        String[] options = new String[]{"Open in background", "Open in new tab", "Add Bookmark", "Copy link text", "Copy link", "Show link", "Share link", "Download", "Block"};
		final String[] imageOptions = new String[]{
			"Open in background", "Open in new tab", "Add Bookmark", "Copy link text", "Copy link", "Show link", "Share link", "Download", "Block",
			"Open image in background", "Open image in new tab", "Copy image link", "Show image link", "Share image link", "Download image"};
		
        if (imageUrl == null) {
            if (linkUrl == null) {
                throw new IllegalArgumentException("Bad null arguments in showLongPressMenu");
            } else {
                // Text link
                url = linkUrl;
                title = linkUrl;
            }
        } else {
            if (linkUrl == null) {
                // Image without link
                url = imageUrl;
                title = "Image: " + imageUrl;
            } else {
                // Image with link
                url = linkUrl;
                title = linkUrl;
                options = imageOptions;
            }
        }
        new AlertDialog.Builder(MainActivity.this)
			.setTitle(title)
			.setItems(options, new OnClickListener() {
				public void onClick(final DialogInterface dialog, final int which) {
					switch (which) {
						case 0:
							newBackgroundTab(url, getCurrentTab().isIncognito);
							break;
						case 1:
							newForegroundTab(url, getCurrentTab().isIncognito);
							break;
						case 2:
							addBookmark(url, title);
							break;
						case 3:
							copyClipboard("Text", text.trim());
							break;
						case 4:
							copyClipboard("URL", url);
							break;
						case 5:
							new AlertDialog.Builder(MainActivity.this)
								.setTitle("Full URL")
								.setMessage(url)
								.setPositiveButton("OK", new EmptyOnClickListener())
								.show();
							break;
						case 6:
							shareUrl(url);
							break;
						case 7:
							startDownload(url, null);
							break;
						case 8:
							if (!url.startsWith("/") && !url.startsWith("file")) {
								final Uri parse = Uri.parse(url);
								//ExceptionLogger.d(TAG, url + ", Authority " + parse.getAuthority() + ", Host " + parse.getHost() + ", LastPathSegment " + parse.getLastPathSegment() + ", Fragment " + parse.getFragment() + ", Path " + parse.getPath());
								addBlockRules(parse.getHost());
							}
							break;
						case 9:
							newBackgroundTab(imageUrl, getCurrentTab().isIncognito);
							break;
						case 10:
							newForegroundTab(imageUrl, getCurrentTab().isIncognito);
							break;
						case 11:
							copyClipboard("URL", imageUrl);
							break;
						case 12:
							new AlertDialog.Builder(MainActivity.this)
								.setTitle("Full imageUrl")
								.setMessage(imageUrl)
								.setPositiveButton("OK", new EmptyOnClickListener())
								.show();
							break;
						case 13:
							shareUrl(imageUrl);
							break;
						case 14:
							startDownload(imageUrl, null);
							break;
					}
				}}).show();
    }

	private boolean startDownload(final String url, String filename) {
        if (!hasOrRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
									null,
									PERMISSION_REQUEST_DOWNLOAD)) {
            return false;
        }
        if (filename == null) {
            filename = URLUtil.guessFileName(url, null, null);
        }
        final DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(url));
        } catch (IllegalArgumentException e) {
            new AlertDialog.Builder(MainActivity.this)
				.setTitle("Can't Download URL")
				.setMessage(url)
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return false;
        }
		ExceptionLogger.d(TAG, downloadLocation+filename);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.fromFile(new File(downloadLocation, filename)));//setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
									   | DownloadManager.Request.NETWORK_MOBILE)
			.setAllowedOverRoaming(false)
			.setTitle("Download")
			.setDescription("Downloading...");
		final String cookie = CookieManager.getInstance().getCookie(url);
        if (cookie != null) {
            request.addRequestHeader("Cookie", cookie);
        }
        final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        assert dm != null;
        getCurrentTab().lastDownload = dm.enqueue(request);
		return true;
    }

	private void queryStatus(final View v, final long lastDownload) {
		final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		final Cursor c = dm.query(new DownloadManager.Query().setFilterById(lastDownload));
		if (c == null) {
			AndroidUtils.toast(MainActivity.this, "Download Not Found");
		} else {
			c.moveToFirst();
			final String name = getClass().getName();
			Log.d(name,
				  "COLUMN_ID: "
				  + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
			Log.d(name,
				  "COLUMN_BYTES_DOWNLOADED_SO_FAR: "
				  + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
			Log.d(name,
				  "COLUMN_LAST_MODIFIED_TIMESTAMP: "
				  + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
			Log.d(name,
				  "COLUMN_LOCAL_URI: "
				  + c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
			Log.d(name,
				  "COLUMN_STATUS: "
				  + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
			Log.d(name,
				  "COLUMN_REASON: "
				  + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));
			AndroidUtils.toast(MainActivity.this, statusMessage(c));
			c.close();
		}
	}

	private String statusMessage(final Cursor c) {
		String msg="???";
		switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
			case DownloadManager.STATUS_FAILED:
				msg = "Download failed";
				break;
			case DownloadManager.STATUS_PAUSED:
				msg = "Download paused";
				break;
			case DownloadManager.STATUS_PENDING:
				msg = "Download pending";
				break;
			case DownloadManager.STATUS_RUNNING:
				msg = "Download in progress";
				break;
			case DownloadManager.STATUS_SUCCESSFUL:
				msg = "Download complete";
				break;
			default:
				msg = "Download is nowhere in sight";
				break;
		}
		return msg;
	}
  
	private final BroadcastReceiver onEvent = new BroadcastReceiver() {
		public void onReceive(final Context ctxt, final Intent i) {
			if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(i.getAction())) {
				queryStatus(toolbar, i.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
			} else if (getCurrentTab().sourceName != null) {
				final long downloadId = i.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
				final Tab currentTab = getCurrentTab();
				if (currentTab.lastDownload == downloadId) {
					final String toString = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + currentTab.sourceName)).toString();
					ExceptionLogger.d(TAG, getCurrentTab().sourceName + ", " + toString);
					loadUrl(toString, currentTab.webview);
					currentTab.sourceName = null;
				}
			}
		}
	};
	
    private void newTabCommon(final WebView webview, boolean isIncognito) {
        //boolean isDesktopUA = !tabs.isEmpty() && getCurrentTab().isDesktopUA;
        final WebSettings settings = webview.getSettings();
		settings.setUserAgentString(isDesktopUA ? desktopUA : androidUA);
        settings.setUseWideViewPort(isDesktopUA);
		settings.setAcceptThirdPartyCookies(accept3PartyCookies);
		settings.setSaveFormData(saveFormData);
		
		settings.setAppCacheEnabled(appCacheEnabled);
		settings.setAllowContentAccess(allowContentAccess);
		settings.setMediaPlaybackRequiresUserGesture(mediaPlaybackRequiresUserGesture);
		settings.setLoadWithOverviewMode(loadWithOverviewMode);
		settings.setDomStorageEnabled(domStorageEnabled);
		settings.setGeolocationEnabled(geolocationEnabled);
		settings.setMixedContentMode(mixedContentMode);
		settings.setDatabaseEnabled(databaseEnabled);
		settings.setOffscreenPreRaster(offscreenPreRaster);
		settings.setUserAgentString(userAgentString);
		settings.setAppCachePath(getExternalFilesDir("cache").getAbsolutePath());
		settings.setDatabasePath(getExternalFilesDir("db").getAbsolutePath());
		
		settings.setAllowFileAccess(allowFileAccess);
		settings.setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs);
		settings.setAllowUniversalAccessFromFileURLs(allowUniversalAccessFromFileURLs);
		settings.setBlockNetworkLoads(blockNetworkLoads);
		settings.setDefaultTextEncodingName(textEncoding);
		settings.setCacheMode(cacheMode);
        if (autoHideToolbar) {
			webview.setOnTouchListener(new TouchListener());
		}
		setRenderMode(webview, renderMode);
		webview.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        webview.setVisibility(View.GONE);
        final Tab tab = new Tab(webview, isIncognito);
		tab.blockCSS = blockCSS;
        tab.blockFonts = blockFonts;
        tab.blockImages = blockImages;
        tab.blockMedia = blockMedia;
        tab.blockJavaScript = blockJavaScript;
        tab.isDesktopUA = isDesktopUA;
        tabs.add(tab);
        webviews.addView(webview);
        setTabCountText(tabs.size());
    }
	
    private void newBackgroundTab(final String url, boolean isIncognito) {
        final WebView webview = createWebView(null);
        newTabCommon(webview, isIncognito);
        loadUrl(url, webview);
    }

    private void newForegroundTab(final String url, boolean isIncognito) {
        final WebView webview = createWebView(null);
        newTabCommon(webview, isIncognito);
        loadUrl(url, webview);
		switchToTab(tabs.size() - 1);
    }

    private void newTabFromBundle(final Bundle bundle, boolean isIncognito) {
        final WebView webview = createWebView(bundle);
        newTabCommon(webview, isIncognito);
    }

    private void switchToTab(final int tab) {
        getCurrentWebView().setVisibility(View.GONE);
        currentTabIndex = tab;
        final Tab currentTab = getCurrentTab();
		currentTab.webview.setVisibility(View.VISIBLE);
        if (currentTab.sourceName != null) {
			final String toString = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + currentTab.sourceName)).toString();
			//ExceptionLogger.d(TAG, getCurrentTab().sourceName + ", " + toString);
			loadUrl(toString, currentTab.webview);
			currentTab.sourceName = null;
			faviconImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			faviconImage.setImageResource(R.drawable.page_info);
			return;
		}
		currentTab.skipTextChange = true;
		if (currentTab.favicon != null) {
			faviconImage.clearColorFilter();
			faviconImage.setImageBitmap(currentTab.favicon);
		} else {
			faviconImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			faviconImage.setBackgroundColor(backgroundColor);
			faviconImage.setImageResource(R.drawable.page_info);
		}
		if (currentTab.loading) {
      		goStop.setImageResource(R.drawable.stop);
			et.setText(currentTab.webview.getUrl());
		} else {
			goStop.setImageResource(R.drawable.reload);
			et.setText(currentTab.webview.getTitle());
		}
		if (currentTab.isIncognito) {
			final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
			final int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);
            final Drawable left = getResources().getDrawable(R.drawable.ic_notification_incognito, null);
			left.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
            left.setBounds(0, 0, size, size);
            et.setCompoundDrawables(left, null, null, null);
            et.setCompoundDrawablePadding(
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, displayMetrics));
		} else {
			et.setCompoundDrawables(null, null, null, null);
		}
		currentTab.webview.requestFocus();
		if (requestList.getVisibility() == View.VISIBLE) {
			log("");
		}
		currentTab.textChanged = false;
		currentTab.skipTextChange = false;
    }

    private void updateFullScreen() {
        final int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        final boolean fullscreenNow = (getWindow().getDecorView().getSystemUiVisibility() & flags) == flags;
        if (fullscreenNow != isFullscreen) {
            getWindow().getDecorView().setSystemUiVisibility(isFullscreen ? flags : 0);
        }
    }
	
	public static File externalLogFilesDir = null;
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		externalLogFilesDir = getExternalFilesDir("logs");
		
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            private Thread.UncaughtExceptionHandler defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                ExceptionLogger.e(TAG, e);
                defaultUEH.uncaughtException(t, e);
            }
        });
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			WebView.enableSlowWholeDocumentDraw();
		}
		
        final IntentFilter f = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		f.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
		registerReceiver(onEvent, f);
		
        try {
            placesDb = new PlacesDbHelper(this).getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e(TAG, "Can't open database", e);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.sweb_activity_main);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				public void onSystemUiVisibilityChange(int p1) {
					updateFullScreen();}});
		isFullscreen = false;
        isNightMode = prefs.getBoolean("night_mode", false);

        webviews = (FrameLayout) findViewById(R.id.webviews);
        currentTabIndex = 0;
		address = (ViewGroup)findViewById(R.id.address);
		faviconImage = (ImageView) findViewById(R.id.favicon);
        et = (AutoCompleteTextView) findViewById(R.id.et);
		goStop = (ImageView) findViewById(R.id.goStop);
		main_layout = findViewById(R.id.main_layout);
		toolbar = (ViewGroup)findViewById(R.id.toolbar);
		searchPane = findViewById(R.id.searchPane);
		requestList = (ListView)findViewById(R.id.requestList);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		
		faviconImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View button) {
					PopupMenu popup = new PopupMenu(MainActivity.this, button);
					//Inflating the Popup using xml file
					popup.getMenuInflater().inflate(R.menu.favicon, popup.getMenu());
					Menu menu = popup.getMenu();
					final Tab currentTab = getCurrentTab();
					menu.findItem(R.id.blockCSS).setChecked(currentTab.blockCSS);
					menu.findItem(R.id.blockFonts).setChecked(currentTab.blockFonts);
					menu.findItem(R.id.blockImages).setChecked(currentTab.blockImages);
					menu.findItem(R.id.blockMedia).setChecked(currentTab.blockMedia);
					menu.findItem(R.id.blockJavaScript).setChecked(currentTab.blockJavaScript);
					
					LogArrayAdapter adapter = (LogArrayAdapter) requestList.getAdapter();
					menu.findItem(R.id.cssInfo).setChecked(requestList.getVisibility() == View.VISIBLE && CSS_PAT.equals(adapter.recentConstraint));
					menu.findItem(R.id.imageLog).setChecked(requestList.getVisibility() == View.VISIBLE && IMAGE_PAT.equals(adapter.recentConstraint));
					menu.findItem(R.id.videoInfo).setChecked(requestList.getVisibility() == View.VISIBLE && MEDIA_PAT.equals(adapter.recentConstraint));
					menu.findItem(R.id.jsInfo).setChecked(requestList.getVisibility() == View.VISIBLE && JAVASCRIPT_PAT.equals(adapter.recentConstraint));
					menu.findItem(R.id.allInfo).setChecked(requestList.getVisibility() == View.VISIBLE && adapter.recentConstraint == null);
					
					menu.findItem(R.id.destopUA).setChecked(currentTab.isDesktopUA);
					
					//registering popup with OnMenuItemClickListener
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
							public boolean onMenuItemClick(MenuItem item) {
								switch (item.getItemId())  {
									case R.id.newIgcognitoTab:
										final WebView webview = createWebView(null);
										newTabCommon(webview, true);
										switchToTab(tabs.size() - 1);
										loadUrl("", webview);
										break;
									case R.id.savePageAsPdf:
										if (printWeb != null) {
											if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
												// Calling createWebPrintJob()
												printTheWebPage(printWeb);
											} else {
												AndroidUtils.toast(MainActivity.this, "Not available for device below Android LOLLIPOP");
											}
										} else {
											AndroidUtils.toast(MainActivity.this, "WebPage not fully loaded");
										}
										break;
									case R.id.savePageAsImage:
										savePageAsImage();
										break;
									case R.id.pageInfo:
										String s = "URL: " + getCurrentWebView().getUrl() + "\n\n";
										s += "Title: " + getCurrentWebView().getTitle() + "\n\n";
										SslCertificate certificate = getCurrentWebView().getCertificate();
										s += certificate == null ? "Not secure" : "Certificate:\n" + AndroidUtils.certificateToStr(certificate);

										new AlertDialog.Builder(MainActivity.this)
											.setTitle("Page info")
											.setMessage(s)
											.setPositiveButton("OK", new EmptyOnClickListener())
											.show();
										break;
									case R.id.imageLog:
										if (item.isChecked())
											requestList.setVisibility(View.GONE);
										else
											log(IMAGE_PAT);
										break;
									case R.id.videoInfo:
										if (item.isChecked())
											requestList.setVisibility(View.GONE);
										else
											log(MEDIA_PAT);
										break;
									case R.id.jsInfo:
										if (item.isChecked())
											requestList.setVisibility(View.GONE);
										else
											log(JAVASCRIPT_PAT);
										break;
									case R.id.cssInfo:
										if (item.isChecked())
											requestList.setVisibility(View.GONE);
										else
											log(CSS_PAT);
										break;
									case R.id.allInfo:
										if (item.isChecked())
											requestList.setVisibility(View.GONE);
										else
											log(null);
										break;
//									case R.id.closeLog:
//										requestList.setVisibility(View.GONE);
//										break;
									case R.id.shareUrl:
										shareUrl(getCurrentWebView().getUrl());
										break;
									case R.id.blockCSS:
										currentTab.blockCSS = !currentTab.blockCSS;
										break;
									case R.id.blockFonts:
										currentTab.blockFonts = !currentTab.blockFonts;
										break;
									case R.id.blockImages: {
											currentTab.blockImages = !currentTab.blockImages;
											WebSettings settings = currentTab.webview.getSettings();
											settings.setBlockNetworkImage(currentTab.blockImages);
											settings.setLoadsImagesAutomatically(!currentTab.blockImages);
											AndroidUtils.toast(MainActivity.this, "Images " + !currentTab.blockImages);
											break;
										}
									case R.id.blockJavaScript:{
											currentTab.blockJavaScript = !currentTab.blockJavaScript;
											break;
										}
									case R.id.blockMedia:
										currentTab.blockMedia = !currentTab.blockMedia;
										break;
									case R.id.viewSource:
										boolean ret = false;
										String url = currentTab.webview.getUrl();
										if (url.startsWith("http")
											|| url.startsWith("ftp")) {
											//currentTab.sourceName = savedName(currentTab.webview) + ".txt";
											//ret = startDownload(currentTab.webview.getUrl(), currentTab.sourceName);
											currentTab.webview.loadData(currentTab.source, "text/txt", "utf-8");
											ret = true;
										}
										if (!ret && currentTab.webview.getSettings().getJavaScriptEnabled()
											&& url.startsWith("http")
											|| url.startsWith("ftp")) {
											if (Build.VERSION.SDK_INT >= 19) {
												currentTab.webview.evaluateJavascript("javascript:(function(){return '<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>';})()", new ValueCallback<String>() {
														@Override
														public void onReceiveValue(String s) {
															currentTab.webview.loadData(AndroidUtils.fixCharCode(s.replaceAll("\\\\n", "\n").replaceAll("\\\\\"", "\"").replaceAll("\\\\b", "\b").replaceAll("\\\\t", "\t").replaceAll("\\\\r", "\r")), "text/txt", "utf-8");
														}
													});
											} 
										} else if (url.startsWith("file")) {
											File f = new File(Uri.decode(url).substring("file:".length()));
											try {
												s = readTextFile(f).toString();
												currentTab.webview.loadData(AndroidUtils.fixCharCode(s.replaceAll("\\\\n", "\n").replaceAll("\\\\\"", "\"").replaceAll("\\\\b", "\b").replaceAll("\\\\t", "\t").replaceAll("\\\\r", "\r")), "text/txt", "utf-8");
											} catch (IOException e) {
												ExceptionLogger.e(TAG, e.getMessage());
											}
										}
										break;
									case R.id.openInApp:
										Intent i = new Intent(Intent.ACTION_VIEW);
										i.setData(Uri.parse(getCurrentWebView().getUrl()));
										try {
											startActivity(i);
										} catch (ActivityNotFoundException e) {
											new AlertDialog.Builder(MainActivity.this)
												.setTitle("Open in app")
												.setMessage("No app can open this URL.")
												.setPositiveButton("OK", new EmptyOnClickListener())
												.show();
										}
										break;
									case R.id.destopUA:
										Tab tab = getCurrentTab();
										tab.isDesktopUA = !tab.isDesktopUA;
										WebView currentWebView = tab.webview;
										WebSettings settings = currentWebView.getSettings();
										settings.setUserAgentString(tab.isDesktopUA ? desktopUA : androidUA);
										settings.setUseWideViewPort(tab.isDesktopUA);
										currentWebView.reload();
										break;
									case R.id.textEncoding:
										encodingDialog(false);
										break;
								}
								return true;
							}
						});

					popup.show();//showing popup menu
				}
		});
		
        registerForContextMenu(et);  
        // setup edit text
        et.setSelected(false);
        urlIntent = getUrlFromIntent(getIntent());
		boolean noPermission = false;
		if (!urlIntent.isEmpty()) {
			if (noPermission = !hasOrRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
										null,
										PERMISSION_REQUEST_READ_EXTERNAL)) {
			}
        }
        et.setText(urlIntent.isEmpty() || noPermission ? "about:blank" : urlIntent);
        et.setAdapter(new SearchAutocompleteAdapter(this, new SearchAutocompleteAdapter.OnSearchCommitListener() {
							  public void onSearchCommit(final String text) {
								  et.setText(text);
								  et.setSelection(text.length());
							  }}));
        et.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
					loadUrl(et.getText().toString(), getCurrentWebView());
				}});

		et.setOnKeyListener(new View.OnKeyListener() {
				public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
						loadUrl(et.getText().toString(), getCurrentWebView());
						return true;
					} else {
						return false;
					}
				}});
		et.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(final CharSequence p1, int p2, int p3, int p4) {
				}
				@Override
				public void onTextChanged(final CharSequence p1, final int p2, final int p3, final int p4) {
					final Tab currentTab = getCurrentTab();
					if (!currentTab.skipTextChange) {
						currentTab.textChanged = true;
						goStop.setImageResource(R.drawable.forward);
					}
				}
				@Override
				public void afterTextChanged(final Editable p1) {
				}
		});
		et.setOnFocusChangeListener(new OnFocusChangeListener() {          
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					final Tab currentTab = getCurrentTab();
					currentTab.skipTextChange = true;
					if (hasFocus) {
						if (!currentTab.loading) {
							et.setText(getCurrentWebView().getUrl());
						}
						et.setSelection(0, et.getText().length());
					} else {
						if (currentTab.loading) {
							et.setText(getCurrentWebView().getUrl());
							goStop.setImageResource(R.drawable.stop);
						} else {
							et.setText(getCurrentWebView().getTitle());
							goStop.setImageResource(R.drawable.reload);
						}
					}
					currentTab.textChanged = false;
					currentTab.skipTextChange = false;
				}
			});
        goStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View p1) {
					final Tab t = getCurrentTab();
					final WebView currentWebView = t.webview;
					if (t.loading) {
						currentWebView.stopLoading();
						goStop.setImageResource(R.drawable.forward);
						t.loading = false;
					} else {
						if (t.textChanged) {
							loadUrl(et.getText().toString(), currentWebView);
						} else {
							t.loading = true;
							currentWebView.reload();
							goStop.setImageResource(R.drawable.stop);
						}
					}
					t.textChanged = false;
				}
		});
        
        searchEdit = (EditText) findViewById(R.id.searchEdit);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                getCurrentWebView().findAllAsync(s.toString());
            }

            @Override
            public void afterTextChanged(final Editable s) {}
        });
        searchCount = (TextView) findViewById(R.id.searchCount);
        (searchFindNext = (ImageView) findViewById(R.id.searchFindNext)).setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					hideKeyboard();
					getCurrentWebView().findNext(true);
				}});
        (searchFindPrev = (ImageView) findViewById(R.id.searchFindPrev)).setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					hideKeyboard();
					getCurrentWebView().findNext(false);
				}});
        (searchClose = (ImageView) findViewById(R.id.searchClose)).setOnClickListener(new View.OnClickListener() {
				public void onClick(final View v) {
					getCurrentWebView().clearMatches();
					searchEdit.setText("");
					searchPane.setVisibility(View.GONE);
					hideKeyboard();
				}});

        useAdBlocker = prefs.getBoolean("adblocker", true);
        initAdblocker();
		
		blockImages = prefs.getBoolean("blockImages", false);
        blockMedia = prefs.getBoolean("blockMedia", false);
        blockCSS = prefs.getBoolean("blockCSS", false);
        blockJavaScript = prefs.getBoolean("blockJavaScript", false);
        blockFonts = prefs.getBoolean("blockFonts", false);
        isLogRequests = prefs.getBoolean("isLogRequests", true);
		enableCookies = prefs.getBoolean("enableCookies", true);
		saveHistory = prefs.getBoolean("saveHistory", true);
		accept3PartyCookies = prefs.getBoolean("accept3PartyCookies", false);
		isFullMenu = prefs.getBoolean("isFullMenu", false);
		saveFormData = prefs.getBoolean("saveFormData", true);
		autoHideToolbar = prefs.getBoolean("autoHideToolbar", false);
        removeIdentifyingHeaders = prefs.getBoolean("removeIdentifyingHeaders", false);
		
		javaScriptEnabled = prefs.getBoolean("javaScriptEnabled", true);
		appCacheEnabled = prefs.getBoolean("appCacheEnabled", true);
		allowContentAccess = prefs.getBoolean("allowContentAccess", true);
		mediaPlaybackRequiresUserGesture = prefs.getBoolean("mediaPlaybackRequiresUserGesture", true);
		loadWithOverviewMode = prefs.getBoolean("loadWithOverviewMode", false);
		domStorageEnabled = prefs.getBoolean("domStorageEnabled", true);
		geolocationEnabled = prefs.getBoolean("geolocationEnabled", false);
		mixedContentMode = prefs.getInt("mixedContentMode", 0);
		databaseEnabled = prefs.getBoolean("databaseEnabled", true);
		offscreenPreRaster = prefs.getBoolean("offscreenPreRaster", false);
		userAgentString = prefs.getString("userAgentString", "Android");
		allowFileAccess = prefs.getBoolean("allowFileAccess", true);
		allowFileAccessFromFileURLs = prefs.getBoolean("allowFileAccessFromFileURLs", true);
		allowUniversalAccessFromFileURLs = prefs.getBoolean("allowUniversalAccessFromFileURLs", true);
		blockNetworkLoads = prefs.getBoolean("blockNetworkLoads", false);
		javaScriptCanOpenWindowsAutomatically = prefs.getBoolean("javaScriptCanOpenWindowsAutomatically", false);
		cacheMode = prefs.getInt("cacheMode", WebSettings.LOAD_DEFAULT);
		isDesktopUA = prefs.getBoolean("isDesktopUA", false);
		requestSaveData = prefs.getBoolean("requestSaveData", true);
		doNotTrack = prefs.getBoolean("doNotTrack", true);
		renderMode = prefs.getInt("renderMode", 0);
		downloadLocation = prefs.getString("downloadLocation", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
		textEncoding = prefs.getString("textEncoding", "UTF-8");
		deleteAfter = prefs.getString("deleteAfter", "30");
		if (placesDb != null)
			placesDb.execSQL("DELETE FROM history WHERE date_created < DATETIME('now', '-" + deleteAfter + " day')", new Object[] {});
		
		setupToolbar(toolbar);
		newBackgroundTab(et.getText().toString(), false);
        final WebView currentWebView = getCurrentWebView();
		currentWebView.setVisibility(View.VISIBLE);
        onNightModeChange();
		gestureDetector = new GestureDetector(this, new CustomGestureListener());
    }
	@Override
    protected void onResume() {
        super.onResume();
        if (printJob != null && printBtnPressed) {
            if (printJob.isCompleted()) {
                AndroidUtils.toast(this, "Printing Completed");
            } else if (printJob.isStarted()) {
                AndroidUtils.toast(this, "Printing isStarted");
            } else if (printJob.isBlocked()) {
                AndroidUtils.toast(this, "Printing isBlocked");
            } else if (printJob.isCancelled()) {
                AndroidUtils.toast(this, "Printing isCancelled");
            } else if (printJob.isFailed()) {
                AndroidUtils.toast(this, "Printing isFailed");
            } else if (printJob.isQueued()) {
                AndroidUtils.toast(this, "Printing isQueued");
            }
            printBtnPressed = false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
		unregisterReceiver(onEvent);
		if (placesDb != null) {
            placesDb.close();
        }
	}

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == FORM_FILE_CHOOSER) {
            if (fileUploadCallback != null) {
                // When the first file chooser activity fails to start due to an intent type not being a mime-type,
                // we should not reset the callback since we'd be called back soon with the */* type.
                if (fileUploadCallbackShouldReset) {
                    fileUploadCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                    fileUploadCallback = null;
                } else {
                    fileUploadCallbackShouldReset = true;
                }
            }
        }
    }
	@Override  
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {  
        super.onCreateContextMenu(menu, v, menuInfo);  
        final MenuInflater inflater = getMenuInflater();
		if (v == et) {
			inflater.inflate(R.menu.address, menu);  
			menu.setHeaderTitle("Select an action");
		} else if (v == faviconImage) {
			inflater.inflate(R.menu.favicon, menu);  
			//menu.setHeaderTitle("Select an action");
		}
    }  
    @Override  
    public boolean onContextItemSelected(final MenuItem item){  
        switch (item.getItemId())  {
			case R.id.paste:
				String clipboardText = getClipboardText().toString();
				et.requestFocus();
				et.setText(clipboardText);
				et.setSelection(clipboardText.length());
				goStop.setImageResource(R.drawable.forward);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				assert imm != null;
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
				break;
			case R.id.pasteOpen:
				clipboardText = getClipboardText() + "";
				et.requestFocus();
				et.setText(clipboardText);
				loadUrl(clipboardText, getCurrentWebView());
				break;
			case R.id.selectAll:
				et.selectAll();
				break;
			case R.id.copyAll:
				copyClipboard("URL", et.getText());
				break;
			case R.id.clear:
				et.setText("");
				break;
		}
        return true;  
    }

	private void log(String pat) {
		if (requestList.getVisibility() == View.GONE) {
			LogArrayAdapter adapter = (LogArrayAdapter) requestList.getAdapter();
			if (adapter == null) {
				adapter = new LogArrayAdapter(MainActivity.this,
														 android.R.layout.simple_list_item_1,
														 getCurrentTab().requestsLog);
				requestList.setAdapter(adapter);
			} 
			adapter.getFilter().filter(pat);
			requestList.setVisibility(View.VISIBLE);
		} else {
			((LogArrayAdapter) requestList.getAdapter()).getFilter().filter(pat);
		}
	}
	
    private void setTabCountText(final int count) {
        if (txtTabCount != null) {
            txtTabCount.setText(String.valueOf(count));
        }
    }

    private void maybeSetupTabCountTextView(final View view, final String name) {
        if ("Show tabs".equals(name)) {
            txtTabCount = (TextView) view.findViewById(R.id.txtText);
        }
		if ("Block Images".equals(name)) {
			blockImagesImageView = (ImageView) view.findViewById(R.id.btnSwipeUp);//(ImageView) view.getChildAt(1)
			if (blockImages) {
				blockImagesImageView.setImageResource(R.drawable.adblocker);
			} else {
				blockImagesImageView.setImageResource(R.drawable.ic_doc_image);
			}
		}
    }

    private void setupToolbar(final ViewGroup parent) {
        final LayoutInflater layoutInflater = getLayoutInflater();
		View v;
		Runnable a1, a2, a3;
		for (String[] actions : toolbarActions) {
            v = layoutInflater.inflate(R.layout.sweb_toolbar_button, parent, false);
            parent.addView(v);
			a1 = null; a2 = null; a3 = null;
            if (actions[0] != null) {
                MenuAction action = getAction(actions[0]);
                ((ImageView) v.findViewById(R.id.btnShortClick)).setImageResource(action.icon);
                maybeSetupTabCountTextView(v, actions[0]);
                a1 = action.action;
            }
            if (actions[1] != null) {
                MenuAction action = getAction(actions[1]);
                ((ImageView) v.findViewById(R.id.btnLongClick)).setImageResource(action.icon);
                maybeSetupTabCountTextView(v, actions[1]);
                a2 = action.action;
            }
            if (actions[2] != null) {
                MenuAction action = getAction(actions[2]);
                ((ImageView) v.findViewById(R.id.btnSwipeUp)).setImageResource(action.icon);
                maybeSetupTabCountTextView(v, actions[2]);
                a3 = action.action;
            }
            setToolbarButtonActions(v, a1, a2, a3);
        }
    }

    void showOpenTabs() {
        final int size = tabs.size();
		final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            items[i] = tabs.get(i).webview.getTitle();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapterWithCurrentItem<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                items,
                currentTabIndex);
        final AlertDialog.Builder tabsDialog = new AlertDialog.Builder(MainActivity.this)
			.setTitle("Tabs")
			.setAdapter(adapter, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switchToTab(which);}});
        if (!closedTabs.isEmpty()) {
            tabsDialog.setNeutralButton("Undo closed tabs", new OnClickListener() {
					public void onClick(final DialogInterface dialog, final int which) {
						final int size = closedTabs.size();
						final String[] items1 = new String[size];
						for (int i = 0; i < size; i++) {
							items1[i] = closedTabs.get(i).title;
						}
						final AlertDialog undoClosedTabsDialog = new AlertDialog.Builder(MainActivity.this)
							.setTitle("Undo closed tabs")
							.setItems(items1, new OnClickListener() {
								public void onClick(DialogInterface dialog, int which1) {
									TitleAndBundle get = closedTabs.get(which1);
									final Bundle bundle = get.bundle;
									closedTabs.remove(which1);
									newTabFromBundle(bundle, get.isIncognito);
									switchToTab(tabs.size() - 1);
								}})
							.create();
						undoClosedTabsDialog.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
								public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
									undoClosedTabsDialog.dismiss();
									new AlertDialog.Builder(MainActivity.this)
										.setTitle("Remove closed tab?")
										.setMessage(closedTabs.get(position).title)
										.setNegativeButton("Cancel", new EmptyOnClickListener())
										.setPositiveButton("Remove", new OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												closedTabs.remove(position);
											}})
										.show();
									return true;
								}});
						undoClosedTabsDialog.show();
					}});
        }
        tabsDialog.show();
    }

    void showTabHistory() {
        final WebBackForwardList list = getCurrentWebView().copyBackForwardList();
        final int size = list.getSize();
        final int idx = size - list.getCurrentIndex() - 1;
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            items[size - i - 1] = list.getItemAtIndex(i).getTitle();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapterWithCurrentItem<String>(
                this,
                android.R.layout.simple_list_item_1,
                items,
                idx);
        new AlertDialog.Builder(this)
			.setTitle("Navigation History")
			.setAdapter(adapter, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					getCurrentWebView().goBackOrForward(idx - which);}})
			.show();
    }
	
	private String savedName(WebView currentWebView) {
		String url = currentWebView.getTitle().replaceAll("[?/\\:*|\"<>#]", "_");
		url = url.length() == 0 ? currentWebView.getUrl() : "/" + url;
		int idx = url.indexOf("/");
		url = idx > 0 ? url.substring(idx) : url;
		idx = url.indexOf("?");
		url = (idx > 0) ? url.substring(0, idx) : url;
		idx = url.indexOf("#");
		url = (idx > 0) ? url.substring(0, idx) : url;
		url = url.length() == 1 ? "/index" : url;
		return url;
	}
	
	String getUniqueName(String outputFolder, String name, String ext) {
        int i = 1;
        File file = new File(outputFolder, name + ext);
        if(file.exists()) {
            while (true) {
                file = new File(outputFolder, name + " (" + i + ")" + ext);
                if (!file.exists())
                    return file.getAbsolutePath();
                i++;
            }
        }
        return file.getAbsolutePath();
    }
	
	void savePageAsImage() {
		if (printWeb != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				// the minimum bitmap height needs to be the view height
				final int bitmapHeight = (printWeb.getMeasuredHeight() < printWeb.getContentHeight())
					? printWeb.getContentHeight() : printWeb.getMeasuredHeight();
				final int bitmapWidth = (printWeb.getMeasuredWidth() < printWeb.getContentWidth())
					? printWeb.getContentWidth() : printWeb.getMeasuredWidth();

				final Bitmap bitmap = Bitmap.createBitmap(
					bitmapWidth, bitmapHeight, 
					Bitmap.Config.ARGB_8888);
				final Canvas canvas = new Canvas(bitmap);
				printWeb.draw(canvas);

				FileOutputStream fos = null;
				BufferedOutputStream bos;
                try {
                    final String savedName = getUniqueName(downloadLocation, savedName(printWeb), ".webp");
					fos = new FileOutputStream(savedName);
					bos = new BufferedOutputStream(fos);
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 100, bos);
					bos.flush();
					fos.flush();
					bos.close();
					fos.close();
					AndroidUtils.toast(this, "Saved " + savedName);
                } catch (Throwable e) {
					ExceptionLogger.e(TAG, e.getMessage());
                }
			} else {
				AndroidUtils.toast(this, "Not available for device below Android LOLLIPOP");
			}
		} else {
			AndroidUtils.toast(MainActivity.this, "WebPage not fully loaded");
		}
	}
	
	// a boolean to check the status of printing
    boolean printBtnPressed = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void printTheWebPage(WebView webView) {
		printBtnPressed = true;
		
        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
			.getSystemService(Context.PRINT_SERVICE);

        // setting the name of job
        String jobName = savedName(webView) + ".pdf";

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
									  new PrintAttributes.Builder().build());
    }
	
    private void initAdblocker() {
        if (useAdBlocker) {
            File externalFilesDir = getExternalFilesDir("adblock");
			adBlocker = new AdBlocker(externalFilesDir);
        } else {
            adBlocker = null;
        }
		ExceptionLogger.d(TAG, "adBlocker " + adBlocker);
    }
	
	void addBlockRules(String address) {
		final File customFilterFile = new File(getExternalFilesDir("adblock").getAbsolutePath() + "/customFilter.txt");
		if (address == null || address.isEmpty()) {
			try {
				if (!customFilterFile.exists()) {
					customFilterFile.createNewFile();
				}
				final String sb = readTextFile(customFilterFile).toString().replaceAll("127.0.0.1 ", "");
				final EditText editView = new EditText(MainActivity.this);
				editView.setText(sb);
				editView.setSelection(sb.length());
				new AlertDialog.Builder(MainActivity.this)
					.setTitle("Edit custom filter")
					.setView(editView)
					.setPositiveButton("Apply", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								final FileWriter fr = new FileWriter(customFilterFile);
								final BufferedWriter br = new BufferedWriter(fr);
								final String[] addresses = editView.getText().toString().split("[\r\n]+");
								String trim;
								for (String s : addresses) {
									trim = s.trim();
									if (trim.length() > 0)
										br.append("127.0.0.1 " + trim + "\n");
								}
								br.flush();
								fr.flush();
								br.close();
								fr.close();
							} catch (IOException e) {
								ExceptionLogger.e(TAG, e.getMessage());
							}
							initAdblocker();
						}})
					.setNegativeButton("Cancel", new EmptyOnClickListener())
					.show();
			} catch (IOException e) {
				ExceptionLogger.e(TAG, e.getMessage());
			}
		} else {
			try {
				final FileWriter fr = new FileWriter(customFilterFile, true);
				final BufferedWriter br = new BufferedWriter(fr);
				br.append("127.0.0.1 " + address.trim() + "\n");
				br.flush();
				fr.flush();
				br.close();
				fr.close();
			} catch (IOException e) {
				ExceptionLogger.e(TAG, e.getMessage());
			}
			initAdblocker();
		}
    }

	private StringBuilder readTextFile(File customFilterFile) throws IOException {
		final FileReader fr = new FileReader(customFilterFile);
		final BufferedReader br = new BufferedReader(fr);
		String ln;
		final StringBuilder sb = new StringBuilder();
		while ((ln = br.readLine()) != null) {
			sb.append(ln).append("\n");
		}
		br.close();
		fr.close();
		return sb;
	}

    void updateAdblockRules() {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<Integer>() {
            @Override
            public Loader<Integer> onCreateLoader(int id, Bundle args) {
                return new AdblockRulesLoader(MainActivity.this, adblockRulesList, getExternalFilesDir("adblock"));
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onLoadFinished(Loader<Integer> loader, Integer data) {
                AndroidUtils.toast(MainActivity.this,
                        String.format("Updated %d / %d adblock subscriptions", data, adblockRulesList.length));
                initAdblocker();
            }

            @Override
            public void onLoaderReset(Loader<Integer> loader) {}
        });
    }
	
	private Cursor cursorHistory;
	private SimpleCursorAdapter historyAdapter;
	private List<Integer> selectedItems;
	private class HistoryHolder {
		final ImageView iconView;
		final ImageView moreView;
		final TextView titleView;
		final TextView domainView;
		final TextView dateView;
		int position;
		Integer id;
		HistoryHolder(final View convertView) {
			moreView = (ImageView) convertView.findViewById(R.id.more);
			iconView = (ImageView) convertView.findViewById(R.id.icon);
			titleView = (TextView) convertView.findViewById(R.id.name);
			domainView = (TextView) convertView.findViewById(R.id.domain);
			dateView = (TextView) convertView.findViewById(R.id.lastAccessed);
			
			moreView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View button) {
						cursorHistory.moveToPosition(position);
						final int rowid = cursorHistory.getInt(cursorHistory.getColumnIndex("_id"));
						final String title = cursorHistory.getString(cursorHistory.getColumnIndex("title"));
						final String url = cursorHistory.getString(cursorHistory.getColumnIndex("url"));
						final PopupMenu popup = new PopupMenu(MainActivity.this, button);
						//Inflating the Popup using xml file
						popup.getMenuInflater().inflate(R.menu.more_history, popup.getMenu());
						
						//registering popup with OnMenuItemClickListener
						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								public boolean onMenuItemClick(MenuItem item) {
									switch (item.getItemId())  {
										case R.id.add: 
											addBookmark(url, title);
											break;
										case R.id.copy:
											copyClipboard("URL", url);
											break;
										case R.id.show:
											new AlertDialog.Builder(MainActivity.this)
												.setTitle(title)
												.setMessage(url)
												.setPositiveButton("OK", new EmptyOnClickListener())
												.show();
											break;
										case R.id.share:
											shareUrl(url);
											break;
										case R.id.delete:
											cursorHistory.close();
											placesDb.execSQL("DELETE FROM history WHERE _id = ?", new Object[] {rowid});
											cursorHistory = placesDb.rawQuery("SELECT title, url, date_created, _id FROM history ORDER BY date_created DESC", null);
											historyAdapter.swapCursor(cursorHistory);
											break;
									}
									return true;
								}
							});

						popup.show();//showing popup menu
					}
				});
			iconView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View button) {
						if (selectedItems.contains(id)) {
							selectedItems.remove(id);
							iconView.setImageResource(R.drawable.dot);
						} else {
							selectedItems.add(id);
							iconView.setImageResource(R.drawable.ic_accept);
						}
					}
			});
			moreView.setTag(this);
			convertView.setTag(this);
			iconView.setTag(this);
			titleView.setTag(this);
		}
	}
	private void showHistory() {
        if (placesDb == null)
			return;
		selectedItems = new ArrayList<>();
        cursorHistory = placesDb.rawQuery("SELECT title, url, date_created, _id FROM history ORDER BY date(date_created) DESC, url ASC", null);
        historyAdapter = new SimpleCursorAdapter(this,
												 R.layout.history_list_item,
												 cursorHistory,
												 new String[] { "title", "url", "date_created" },
												 new int[] { R.id.name, R.id.domain , R.id.lastAccessed }) {
			@Override
			public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
				final HistoryHolder holder;
				if (convertView == null) {
					convertView = super.getView(position, convertView, parent);//getLayoutInflater().inflate(R.layout.list_item, parent, false);
					holder = new HistoryHolder(convertView);
				} else {
					holder = (HistoryHolder) convertView.getTag();
				}
				holder.position = position;
				cursorHistory.moveToPosition(position);
				holder.id = cursorHistory.getInt(cursorHistory.getColumnIndex("_id"));
				if (selectedItems.contains(holder.id)) {
					holder.iconView.setImageResource(R.drawable.ic_accept);
				} else {
					holder.iconView.setImageResource(R.drawable.dot);
				}
				final TextView titleView = holder.titleView;
				final TextView domainView = holder.domainView;
				final TextView dateView = holder.dateView;
				final String title = cursorHistory.getString(cursorHistory.getColumnIndex("title"));
				titleView.setText(title);
				final String url = cursorHistory.getString(cursorHistory.getColumnIndex("url"));
				domainView.setText(url);
				final String lastAccessed = cursorHistory.getString(cursorHistory.getColumnIndex("date_created"));
				dateView.setText(lastAccessed);
				return convertView;
			}
		};
		final AlertDialog dialog = new AlertDialog.Builder(this)
			.setTitle("History")
			.setPositiveButton("OK", new EmptyOnClickListener())
			.setNegativeButton("Delete", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					final int size = selectedItems.size();
					if (size > 0) {
						final StringBuilder sb = new StringBuilder();
						for (int i = 0; i < size; i++) {
							sb.append("?");
							if (i < size - 1) {
								sb.append(",");
							}
						}
						cursorHistory.close();
						placesDb.execSQL("DELETE FROM history WHERE _id IN (" + sb.toString()+ ")", selectedItems.toArray());
					}
					
				}})
			.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface p1) {
					cursorHistory.close();}})
			.setAdapter(historyAdapter, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					cursorHistory.moveToPosition(which);
					String url = cursorHistory.getString(cursorHistory.getColumnIndex("url"));
					et.requestFocus();
					et.setText(url);
					loadUrl(url, getCurrentWebView());
					cursorHistory.close();
				}})
			.create();
        dialog.show();
	}
	private class BookmarkHolder {
		final ImageView iconView;
		final ImageView moreView;
		final TextView titleView;
		final TextView domainView;
		int position;
		Integer id;
		BookmarkHolder(final View convertView) {
			moreView = (ImageView) convertView.findViewById(R.id.more);
			iconView = (ImageView) convertView.findViewById(R.id.icon);
			titleView = (TextView) convertView.findViewById(R.id.name);
			domainView = (TextView) convertView.findViewById(R.id.domain);
			
			moreView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View button) {
						bookmarkCursor.moveToPosition(position);
						final int rowid = bookmarkCursor.getInt(bookmarkCursor.getColumnIndex("_id"));
						final String title = bookmarkCursor.getString(bookmarkCursor.getColumnIndex("title"));
						final String url = bookmarkCursor.getString(bookmarkCursor.getColumnIndex("url"));
						final PopupMenu popup = new PopupMenu(MainActivity.this, button);
						
						popup.getMenuInflater().inflate(R.menu.more_bookmark, popup.getMenu());

						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								public boolean onMenuItemClick(MenuItem item) {
									switch (item.getItemId())  {
										case R.id.rename: 
											final EditText editView2 = new EditText(MainActivity.this);
											editView2.setText(title);
											new AlertDialog.Builder(MainActivity.this)
												.setTitle("Rename bookmark")
												.setView(editView2)
												.setPositiveButton("Rename", new OnClickListener() {
													public void onClick(DialogInterface dlg, int which) {
														bookmarkCursor.close();
														placesDb.execSQL("UPDATE bookmarks SET title=? WHERE _id=?", new Object[] {editView2.getText(), rowid});
														bookmarkCursor = placesDb.rawQuery("SELECT title, url, _id FROM bookmarks", null);
														bookmarkAdapter.swapCursor(bookmarkCursor);
														
													}})
												.setNegativeButton("Cancel", new EmptyOnClickListener())
												.show();
											break;
										case R.id.change:
											final EditText editView = new EditText(MainActivity.this);
											editView.setText(url);
											new AlertDialog.Builder(MainActivity.this)
												.setTitle("Change bookmark URL")
												.setView(editView)
												.setPositiveButton("Change URL", new OnClickListener() {
													public void onClick(DialogInterface dlg, int which) {
														bookmarkCursor.close();
														placesDb.execSQL("UPDATE bookmarks SET url=? WHERE _id=?", new Object[] {editView.getText(), rowid});
														bookmarkCursor = placesDb.rawQuery("SELECT title, url, _id FROM bookmarks", null);
														bookmarkAdapter.swapCursor(bookmarkCursor);
													}})
												.setNegativeButton("Cancel", new EmptyOnClickListener())
												.show();
											break;
										case R.id.copy:
											copyClipboard("URL", url);
											break;
										case R.id.share:
											shareUrl(url);
											break;
										case R.id.delete:
											bookmarkCursor.close();
											placesDb.execSQL("DELETE FROM bookmarks WHERE _id = ?", new Object[] {rowid});
											bookmarkCursor = placesDb.rawQuery("SELECT title, url, _id FROM bookmarks", null);
											bookmarkAdapter.swapCursor(bookmarkCursor);
											break;
									}
									return true;
								}
							});

						popup.show();
					}
				});
			iconView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View button) {
						if (selectedItems.contains(id)) {
							selectedItems.remove(id);
							iconView.setImageResource(R.drawable.dot);
						} else {
							selectedItems.add(id);
							iconView.setImageResource(R.drawable.ic_accept);
						}
					}
				});
			moreView.setTag(this);
			convertView.setTag(this);
			iconView.setTag(this);
			titleView.setTag(this);
		}
	}
	private SimpleCursorAdapter bookmarkAdapter;
	private Cursor bookmarkCursor;
    private void showBookmarks() {
        if (placesDb == null)
			return;
        selectedItems = new ArrayList<>();
        bookmarkCursor = placesDb.rawQuery("SELECT title, url, _id FROM bookmarks", null);
        bookmarkAdapter = new SimpleCursorAdapter(this,
												  R.layout.bookmark_list_item,
												  bookmarkCursor,
												  new String[] { "title", "url" },
												  new int[] { R.id.name, R.id.domain }) {
			@Override
			public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
				final BookmarkHolder holder;
				if (convertView == null) {
					convertView = super.getView(position, convertView, parent);//getLayoutInflater().inflate(R.layout.list_item, parent, false);
					holder = new BookmarkHolder(convertView);
				} else {
					holder = (BookmarkHolder) convertView.getTag();
				}
				holder.position = position;
				bookmarkCursor.moveToPosition(position);
				holder.id = bookmarkCursor.getInt(bookmarkCursor.getColumnIndex("_id"));
				if (selectedItems.contains(holder.id)) {
					holder.iconView.setImageResource(R.drawable.ic_accept);
				} else {
					holder.iconView.setImageResource(R.drawable.dot);
				}
				final TextView titleView = holder.titleView;
				final TextView domainView = holder.domainView;
				final String title = bookmarkCursor.getString(bookmarkCursor.getColumnIndex("title"));
				titleView.setText(title);
				final String url = bookmarkCursor.getString(bookmarkCursor.getColumnIndex("url"));
				domainView.setText(url);
				return convertView;
			}
		};
		final AlertDialog dialog = new AlertDialog.Builder(this)
			.setTitle("Bookmarks")
			.setPositiveButton("OK", new EmptyOnClickListener())
			.setNegativeButton("Delete", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					final int size = selectedItems.size();
					if (size > 0) {
						final StringBuilder sb = new StringBuilder();
						for (int i = 0; i < size; i++) {
							sb.append("?");
							if (i < size - 1) {
								sb.append(",");
							}
						}
						bookmarkCursor.close();
						placesDb.execSQL("DELETE FROM bookmarks WHERE _id IN (" + sb.toString()+ ")", selectedItems.toArray());
					}

				}})
			.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(android.content.DialogInterface p1) {
					bookmarkCursor.close();}})
			.setAdapter(bookmarkAdapter, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					bookmarkCursor.moveToPosition(which);
					String url = bookmarkCursor.getString(bookmarkCursor.getColumnIndex("url"));
					et.requestFocus();
					et.setText(url);
					loadUrl(url, getCurrentWebView());
					bookmarkCursor.close();
				}})
			.create();
        dialog.show();
    }

	private CharSequence getClipboardText() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) { //Android 2.3 and below
			final android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboard.getText();
		} else { //Android 3.0 and higher
			final android.content.ClipboardManager cm = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			//ClipDescription description = cm.getPrimaryClipDescription();
            ClipData clipData = cm.getPrimaryClip();
            if (clipData != null)
                return clipData.getItemAt(0).getText();
            else
                return null;
        }
    }

	private void copyClipboard(CharSequence label, CharSequence text) {
		final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		assert clipboard != null;
		final ClipData clipData = ClipData.newPlainText(label, text);
		clipboard.setPrimaryClip(clipData);
		AndroidUtils.toast(MainActivity.this, "Copied \"" + text + "\" to clipboard");
	}

    private void addHistory(final WebView currentWebView, final String url) {
        if (placesDb == null)
			return;
        
		final String selection = "url=? AND date(date_created)=?" ;
		final Calendar cal = Calendar.getInstance();
		final int month = (cal.get(Calendar.MONTH) + 1);
		final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		final String currentDate = cal.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : month) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
		final String[] selectionArgs = new String[] {url, currentDate};
		final int deleted = placesDb.delete("history", selection, selectionArgs);
		//ExceptionLogger.d(TAG, "deleted " + deleted + ", currentDate: " + currentDate);

		final ContentValues valuesInsert = new ContentValues(2);
        valuesInsert.put("title", currentWebView.getTitle());
        valuesInsert.put("url", url);
        placesDb.insert("history", null, valuesInsert);
    }

    private void addBookmark() {
        final WebView currentWebView = getCurrentWebView();
		addBookmark(currentWebView.getUrl(), currentWebView.getTitle());
	}

    private void addBookmark(String url, String title) {
        if (placesDb == null || url.isEmpty() || url.equalsIgnoreCase("about:blank"))
			return;
        final ContentValues values = new ContentValues(2);
        values.put("title", title);
		values.put("url", url);
        placesDb.insert("bookmarks", null, values);
		AndroidUtils.toast(MainActivity.this, "Added " + url + " to bookmarks");
	}

    private void exportBookmarks() {
        if (placesDb == null) {
            new AlertDialog.Builder(this)
				.setTitle("Export bookmarks error")
				.setMessage("Can't open bookmarks database")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }
        if (!hasOrRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                null,
                PERMISSION_REQUEST_EXPORT_BOOKMARKS)) {
            return;
        }
        final File file = new File(Environment.getExternalStorageDirectory(), "bookmarks.html");
        if (file.exists()) {
            new AlertDialog.Builder(this)
				.setTitle("Export bookmarks")
				.setMessage("The file bookmarks.html already exists on SD card. Overwrite?")
				.setNegativeButton("Cancel", new EmptyOnClickListener())
				.setPositiveButton("Overwrite", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
                        //noinspection ResultOfMethodCallIgnored
                        file.delete();
                        exportBookmarks();
                    }})
				.show();
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                    "<!-- This is an automatically generated file.\n" +
                    "     It will be read and overwritten.\n" +
                    "     DO NOT EDIT! -->\n" +
                    "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                    "<TITLE>Bookmarks</TITLE>\n" +
                    "<H1>Bookmarks Menu</H1>\n" +
                    "\n" +
                    "<DL><p>\n");
            try (Cursor cursor = placesDb.rawQuery("SELECT title, url FROM bookmarks", null)) {
                final int titleIdx = cursor.getColumnIndex("title");
                final int urlIdx = cursor.getColumnIndex("url");
                while (cursor.moveToNext()) {
                    bw.write("    <DT><A HREF=\"" + cursor.getString(urlIdx) + "\" ADD_DATE=\"0\" LAST_MODIFIED=\"0\">");
                    bw.write(Html.escapeHtml(cursor.getString(titleIdx)));
                    bw.write("</A>\n");
                }
            }
            bw.write("</DL>\n");
            bw.close();
            AndroidUtils.toast(this, "Bookmarks exported to bookmarks.html on SD card");
        } catch (IOException e) {
            new AlertDialog.Builder(this)
				.setTitle("Export bookmarks error")
				.setMessage(e.toString())
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
        }
    }

    @SuppressLint("DefaultLocale")
    private void importBookmarks() {
        if (placesDb == null) {
            new AlertDialog.Builder(this)
				.setTitle("Import bookmarks error")
				.setMessage("Can't open bookmarks database")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }
        if (!hasOrRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                null,
                PERMISSION_REQUEST_IMPORT_BOOKMARKS)) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), "bookmarks.html");
        StringBuilder sb = new StringBuilder();
        try {
            char[] buf = new char[16*1024];
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            int count;
            while ((count = br.read(buf)) != -1) {
                sb.append(buf, 0, count);
            }
            br.close();
        } catch (FileNotFoundException e) {
            new AlertDialog.Builder(this)
				.setTitle("Import bookmarks error")
				.setMessage("Bookmarks should be placed in a bookmarks.html file on the SD Card")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        } catch (IOException e) {
            new AlertDialog.Builder(this)
				.setTitle("Import bookmarks error")
				.setMessage(e.toString())
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }

        ArrayList<TitleAndUrl> bookmarks = new ArrayList<>();
        Pattern pattern = Pattern.compile("<A HREF=\"([^\"]*)\"[^>]*>([^<]*)</A>");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) {
            TitleAndUrl pair = new TitleAndUrl();
            pair.url = matcher.group(1);
            pair.title = matcher.group(2);
            if (pair.url == null || pair.title == null) continue;
            pair.title = Html.fromHtml(pair.title).toString();
            bookmarks.add(pair);
        }

        if (bookmarks.isEmpty()) {
            new AlertDialog.Builder(this)
				.setTitle("Import bookmarks")
				.setMessage("No bookmarks found in bookmarks.html")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }

        try {
            placesDb.beginTransaction();
            SQLiteStatement stmt = placesDb.compileStatement("INSERT INTO bookmarks (title, url) VALUES (?,?)");
            for (TitleAndUrl pair : bookmarks) {
                stmt.bindString(1, pair.title);
                stmt.bindString(2, pair.url);
                stmt.execute();
            }
            placesDb.setTransactionSuccessful();
            AndroidUtils.toast(this, String.format("Imported %d bookmarks", bookmarks.size()));
        } finally {
            placesDb.endTransaction();
        }
    }

    private void deleteAllBookmarks() {
        if (placesDb == null) {
            new AlertDialog.Builder(this)
				.setTitle("Bookmarks error")
				.setMessage("Can't open bookmarks database")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }
        new AlertDialog.Builder(this)
			.setTitle("Delete all bookmarks?")
			.setMessage("This action cannot be undone")
			.setNegativeButton("Cancel", new EmptyOnClickListener())
			.setPositiveButton("Delete All", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					placesDb.execSQL("DELETE FROM bookmarks");}})
			.show();
    }

    private void deleteAllHistory() {
        if (placesDb == null) {
            new AlertDialog.Builder(this)
				.setTitle("History error")
				.setMessage("Can't open history database")
				.setPositiveButton("OK", new EmptyOnClickListener())
				.show();
            return;
        }
        new AlertDialog.Builder(this)
			.setTitle("Delete all history?")
			.setMessage("This action cannot be undone")
			.setNegativeButton("Cancel", new EmptyOnClickListener())
			.setPositiveButton("Delete All", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					placesDb.execSQL("DELETE FROM history");}})
			.show();
    }

    private void closeCurrentTab() {
        WebView currentWebView = getCurrentWebView();
		String url = currentWebView.getUrl();
		if (url != null && !url.equals("about:blank")) {
            TitleAndBundle titleAndBundle = new TitleAndBundle();
            titleAndBundle.title = currentWebView.getTitle();
            titleAndBundle.bundle = new Bundle();
			titleAndBundle.isIncognito = getCurrentTab().isIncognito;
            currentWebView.saveState(titleAndBundle.bundle);
            closedTabs.add(0, titleAndBundle);
            if (closedTabs.size() > 500) {
                closedTabs.remove(closedTabs.size() - 1);
            }
        }
        ((FrameLayout) findViewById(R.id.webviews)).removeView(currentWebView);
        currentWebView.destroy();
        tabs.remove(currentTabIndex);
        if (currentTabIndex >= tabs.size()) {
            currentTabIndex = tabs.size() - 1;
        }
        if (currentTabIndex == -1) {
            // We just closed the last tab
            newBackgroundTab("", false);
            currentTabIndex = 0;
        }
        currentWebView = getCurrentWebView();
		currentWebView.setVisibility(View.VISIBLE);
		Tab currentTab = getCurrentTab();
		if (currentTab.isIncognito) {
			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);
            Drawable left = getResources().getDrawable(R.drawable.ic_notification_incognito, null);
            left.setBounds(0, 0, size, size);
            et.setCompoundDrawables(left, null, null, null);
            et.setCompoundDrawablePadding(
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, displayMetrics));
		} else {
			et.setCompoundDrawables(null, null, null, null);
		}
		currentTab.skipTextChange = true;
		if (currentTab.loading) {
     		goStop.setImageResource(R.drawable.stop);
			et.setText(currentWebView.getUrl());
		} else {
			goStop.setImageResource(R.drawable.reload);
			et.setText(currentWebView.getTitle());
		}
		setTabCountText(tabs.size());
		if (currentTab.favicon != null) {
			faviconImage.clearColorFilter();
			faviconImage.setImageBitmap(currentTab.favicon);
		} else {
			faviconImage.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			faviconImage.setBackgroundColor(backgroundColor);
			faviconImage.setImageResource(R.drawable.page_info);
		}
		currentTab.webview.requestFocus();
		currentTab.textChanged = false;
		currentTab.skipTextChange = false;
	}

    private String getUrlFromIntent(final Intent intent) {
//		ExceptionLogger.log(TAG, "getUrlFromIntent " + intent.getAction());
//		ExceptionLogger.log(TAG, "getDataString " + intent.getDataString());
//		ExceptionLogger.log(TAG, "Intent.EXTRA_TEXT " + intent.getStringExtra(Intent.EXTRA_TEXT));
//		ExceptionLogger.log(TAG, "query " + intent.getStringExtra("query"));
		if (Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction())
			|| Intent.ACTION_SEND.equals(intent.getAction())
			|| Intent.ACTION_VIEW.equals(intent.getAction())
			|| Intent.ACTION_SENDTO.equals(intent.getAction())) {
			Uri uri = intent.getData();
			ExceptionLogger.d(TAG, "URI to open is: " + uri + ", intent " + intent + ", " + intent.getClipData());
			if (uri != null) {
				final String scheme = uri.getScheme();
				if (!scheme.startsWith("http") && !scheme.startsWith("ftp") && !scheme.startsWith("file")) {
					return AndroidUtils.getPath(MainActivity.this, uri);
				} else {
					return uri.toString();
				}
			} else {
				final ClipData clip = intent.getClipData();
				if (clip != null) {
					final int itemCount = clip.getItemCount();
					if (itemCount > 0) {
						uri = clip.getItemAt(0).getUri();
						return AndroidUtils.getPath(MainActivity.this, uri);
					}
				}
			}
		}
        if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getData() != null) {
            return intent.getDataString();
        } else if (Intent.ACTION_SEND.equals(intent.getAction()) && "text/plain".equals(intent.getType())) {
            return intent.getStringExtra(Intent.EXTRA_TEXT);
        } else if (Intent.ACTION_WEB_SEARCH.equals(intent.getAction()) && intent.getStringExtra("query") != null) {
            return intent.getStringExtra("query");
        } else {
            return "";
        }
    }

	private String urlIntent;
    @Override
    protected void onNewIntent(Intent intent) {
        urlIntent = getUrlFromIntent(intent);
        if (!urlIntent.isEmpty()) {
			if (!hasOrRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
										null,
										PERMISSION_REQUEST_READ_EXTERNAL)) {
				return;
			}
            newBackgroundTab(urlIntent, false);
            switchToTab(tabs.size() - 1);
        }
    }
	
    private void onNightModeChange() {
		if (isNightMode) {
            textColor = Color.rgb(0xe0, 0xe0, 0xe0);
            backgroundColor = Color.rgb(0x22, 0x22, 0x22);
            progressbar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 0x66, 0)));
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(Color.BLACK);
			for (Tab tab : tabs) {
				tab.webview.setBackgroundColor(DARK_BACKGROUND);
			}
        } else {
            textColor = Color.BLACK;
            backgroundColor = Color.rgb(0xe0, 0xe0, 0xe0);
            progressbar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 0xcc, 0)));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			for (Tab tab : tabs) {
				tab.webview.setBackgroundColor(LIGHT_BACKGROUND);
			}
        }
		et.setTextColor(textColor);
		et.setBackgroundColor(backgroundColor);
		Drawable compoundDrawables = et.getCompoundDrawables()[0];
		if (compoundDrawables != null)
			compoundDrawables.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
		goStop.setColorFilter(textColor);
		goStop.setBackgroundColor(backgroundColor);
		faviconImage.setBackgroundColor(backgroundColor);
		
		searchEdit.setTextColor(textColor);
		searchEdit.setBackgroundColor(backgroundColor);
		searchCount.setTextColor(textColor);
		searchCount.setBackgroundColor(backgroundColor);
		requestList.setBackgroundColor(backgroundColor);
		searchPane.setBackgroundColor(backgroundColor);
		searchFindPrev.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
		searchFindNext.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
		searchClose.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
		toolbar.setBackgroundColor(backgroundColor);
		final int childCount = toolbar.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View v = toolbar.getChildAt(i);
			final ImageButton btnShortClick = (ImageButton)v.findViewById(R.id.btnShortClick);
			btnShortClick.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			
			final ImageButton btnSwipeUp = (ImageButton)v.findViewById(R.id.btnSwipeUp);
			btnSwipeUp.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			
			final ImageButton btnLongClick = (ImageButton)v.findViewById(R.id.btnLongClick);
			btnLongClick.setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
			
			final TextView txtText = (TextView)v.findViewById(R.id.txtText);
			txtText.setTextColor(textColor);
		}
//        for (int i = 0; i < tabs.size(); i++) {
//            tabs.get(i).webview.setBackgroundColor(isNightMode ? Color.BLACK : Color.WHITE);
//            injectCSS(tabs.get(i).webview);
//        }
    }

    /**
     * This method forces the layer type to hardware, which
     * enables hardware rendering on the WebView instance
     * of the current LightningView.
     */
    private void setHardwareRendering(WebView webview) {
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    /**
     * This method sets the layer type to none, which
     * means that either the GPU and CPU can both compose
     * the layers when necessary.
     */
    private void setNormalRendering(WebView webview) {
        webview.setLayerType(View.LAYER_TYPE_NONE, null);
    }

    /**
     * This method forces the layer type to software, which
     * disables hardware rendering on the WebView instance
     * of the current LightningView and makes the CPU render
     * the view.
     */
    void setSoftwareRendering(WebView webview) {
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

	/**
	 * Sets the current rendering color of the WebView instance
	 * of the current LightningView. The for modes are normal
	 * rendering, inverted rendering, grayscale rendering,
	 * and inverted grayscale rendering
	 *
	 * @param mode the integer mode to set as the rendering mode.
	 * see the numbers in documentation above for the
	 * values this method accepts.
	 */
	private void setRenderMode(WebView webview, int which) {
		switch (which) {
			case 0: {
					paint.setColorFilter(null);
					// setSoftwareRendering(); // Some devices get segfaults
					// in the WebView with Hardware Acceleration enabled,
					// the only fix is to disable hardware rendering
					setNormalRendering(webview);
					invertPage = false;
					break;
				}
			case 1: {
					ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(
						negativeColorArray);
					paint.setColorFilter(filterInvert);
					setHardwareRendering(webview);
					invertPage = true;
					break;
				}
			case 2:
				ColorMatrix cm = new ColorMatrix();
				cm.setSaturation(0f);
				ColorMatrixColorFilter filterGray = new ColorMatrixColorFilter(cm);
				paint.setColorFilter(filterGray);
				setHardwareRendering(webview);
				break;
			case 3:
				ColorMatrix matrix = new ColorMatrix();
				matrix.set(negativeColorArray);
				ColorMatrix matrixGray = new ColorMatrix();
				matrixGray.setSaturation(0f);
				ColorMatrix concat = new ColorMatrix();
				concat.setConcat(matrix, matrixGray);
				ColorMatrixColorFilter filterInvertGray = new ColorMatrixColorFilter(concat);
				paint.setColorFilter(filterInvertGray);
				setHardwareRendering(webview);
				invertPage = true;
				break;
			case 4:
				ColorMatrixColorFilter increaseHighContrast = new ColorMatrixColorFilter(increaseContrastColorArray);
				paint.setColorFilter(increaseHighContrast);
				setHardwareRendering(webview);
				break;
		}
	}
	
	/**
     * The OnTouchListener used by the WebView so we can
     * get scroll events and show/hide the action bar when
     * the page is scrolled up/down.
     */
    private class TouchListener implements View.OnTouchListener {

        float location = 0f;
        float y = 0f;
        int action = 0;

        @SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View view, MotionEvent arg1) {
            if (view == null)
				return false;
            if (!view.hasFocus()) {
                view.requestFocus();
            }
            action = arg1.getAction();
            y = arg1.getY();
            if (action == MotionEvent.ACTION_DOWN) {
                location = y;
            } else if (action == MotionEvent.ACTION_UP) {
                float distance = y - location;
                if (distance > SCROLL_UP_THRESHOLD && view.getScrollY() < SCROLL_UP_THRESHOLD) {
                    toolbar.setVisibility(View.VISIBLE);
                } else if (distance < -SCROLL_UP_THRESHOLD) {
                    toolbar.setVisibility(View.GONE);
                }
                location = 0f;
            }
            gestureDetector.onTouchEvent(arg1);
            return false;
        }
    }

    /**
     * The SimpleOnGestureListener used by the [TouchListener]
     * in order to delegate show/hide events to the action bar when
     * the user flings the page. Also handles long press events so
     * that we can capture them accurately.
     */
    private class CustomGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float power = (int) (velocityY * 100 / maxFling);
			if (power < -10) {
				toolbar.setVisibility(View.GONE);
			} else if (power > 15) {
				toolbar.setVisibility(View.VISIBLE);
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
    }

	AlertDialog alertDialog;
    void showMenu() {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
		if (isFullMenu) {
			showFullMenu();
		} else {
			showShortMenu();
		}
	}

    private void showShortMenu() {
        final ArrayList<MenuAction> shortMenuActions = new ArrayList<>(shortMenu.length);
        for (int i = 0; i < shortMenu.length; i++) {
            shortMenuActions.add(getAction(shortMenu[i]));
        }
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		ListView tv = new ListView(this);
		MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
			MainActivity.this,
			android.R.layout.simple_list_item_1,
			shortMenuActions);
        tv.setAdapter(adapter);
		builder.setTitle("Actions")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					alertDialog = null;
				}
			});
		builder.setView(tv);
		alertDialog = builder.create();
		alertDialog.show();
    }

    private void showFullMenu() {
		final ArrayList<MenuAction> copyOfRange = new ArrayList<MenuAction>(menuActions.length - toolbarActions.length*toolbarActions[0].length);
		for (MenuAction ma : menuActions) {
			boolean exist = false;
			for (String[] arr : toolbarActions) {
				for (String st : arr) {
					if (ma.title.equals(st)) {
						exist = true;
						break;
					}
				}
				if (exist) 
					break;
			}
			if (!exist) {
				copyOfRange.add(ma);
			}
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		ListView tv = new ListView(this);
		MenuActionArrayAdapter adapter = new MenuActionArrayAdapter(
			MainActivity.this,
			android.R.layout.simple_list_item_1,
			copyOfRange);
        tv.setAdapter(adapter);
		builder.setTitle("Full menu")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					alertDialog = null;
				}
			});
		builder.setView(tv);
		alertDialog = builder.create();
		alertDialog.show();
    }

    private void shareUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share URL"));
    }

    public void loadUrl(String url, final WebView webview) {
		//ExceptionLogger.d("loadUrl ", url);
        url = url.trim();
        if (url.isEmpty()) {
            url = "about:blank";
        }
		if (url.startsWith("/")) {
			url = Uri.fromFile(new File(Uri.decode(url))).toString();
		}
        Tab currentTab = null;
		for (Tab t : tabs) {
			if (t.webview == webview) {
				currentTab = t;
				break;
			}
		}
		if (url.startsWith("javascript:") 
			|| url.startsWith("file:") 
			|| url.startsWith("data:")) {
			currentTab.blockImages = false;
			currentTab.blockJavaScript = false;
			currentTab.blockCSS = false;
			currentTab.blockFonts = false;
			currentTab.blockMedia = false;
			final WebSettings settings = webview.getSettings();
			settings.setBlockNetworkImage(false);
			settings.setLoadsImagesAutomatically(true);
			settings.setJavaScriptEnabled(true);
			settings.setJavaScriptCanOpenWindowsAutomatically(true);
			if (url.startsWith("file:")) {
				urlIntent = url;
				if (!hasOrRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
											null,
											PERMISSION_REQUEST_READ_EXTERNAL)) {
					return;
				}
			}
		} else if (url.startsWith("content")) {
			url = AndroidUtils.getPath(MainActivity.this, Uri.parse(url));
        } else {
			if (url.indexOf(' ') == -1 && Patterns.WEB_URL.matcher(url).matches()) {
				final int indexOfHash = url.indexOf('#');
				final String guess = URLUtil.guessUrl(url);
				//ExceptionLogger.log("guess1 ", guess);
				if (indexOfHash != -1 && guess.indexOf('#') == -1) {
					// Hash exists in original URL but no hash in guessed URL
					url = guess + url.substring(indexOfHash);
				} else {
					url = guess;
				}
				//ExceptionLogger.log("guess2 ", guess);
			} else if (!url.equals("about:blank")) {
				url = URLUtil.composeSearchUrl(url, searchUrl, "%s");
			}
			final WebSettings settings = webview.getSettings();
			settings.setJavaScriptEnabled(javaScriptEnabled);
			settings.setJavaScriptCanOpenWindowsAutomatically(javaScriptCanOpenWindowsAutomatically);
			settings.setBlockNetworkImage(currentTab.blockImages);
			settings.setLoadsImagesAutomatically(!currentTab.blockImages);
		}
		//ExceptionLogger.log("url2 ", url);
		final ArrayMap<String, String> requestHeaders = new ArrayMap<String, String>();
		if (requestSaveData) {
			requestHeaders.put("Save-Data", "on");
		} else {
			requestHeaders.remove("Save-Data");
		}
		if (doNotTrack) {
			requestHeaders.put("DNT", "1");
		} else {
			requestHeaders.remove("DNT");
		}
		if (removeIdentifyingHeaders) {
			requestHeaders.put("X-Requested-With", "");
			requestHeaders.put("X-Wap-Profile", "");
		}
		if (currentTab.webview == webview && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if (currentTab.isIncognito) {
				mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW;
			} else {
				mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;
			}
		} 
		if (currentTab.isIncognito) {
			WebSettings settings = webview.getSettings();
			settings.setDomStorageEnabled(false);
			settings.setAppCacheEnabled(false);
			settings.setDatabaseEnabled(false);
			settings.setGeolocationEnabled(false);
			settings.setAcceptThirdPartyCookies(false);
			settings.setSaveFormData(false);
			settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);
            Drawable left = getResources().getDrawable(R.drawable.ic_notification_incognito, null);
            left.setBounds(0, 0, size, size);
            et.setCompoundDrawables(left, null, null, null);
            et.setCompoundDrawablePadding(
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, displayMetrics));
		}
		currentTab.loading = true;
		currentTab.textChanged = false;
		webview.loadUrl(url, requestHeaders);
		if (currentTab == getCurrentTab()) {
			goStop.setImageResource(R.drawable.stop);
		}
		hideKeyboard();
	}

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }
	
	private static final long TIME_INTERVAL = 250000000L;
	private long mBackPressed = System.nanoTime();
    @Override
    public void onBackPressed() {
        if ((findViewById(R.id.fullScreenVideo)).getVisibility() == View.VISIBLE && fullScreenCallback[0] != null) {
            fullScreenCallback[0].onCustomViewHidden();
        } else if (getCurrentWebView().canGoBack()) {
            getCurrentWebView().goBack();
        } else if (tabs.size() > 1) {
            closeCurrentTab();
        } else if (mBackPressed + TIME_INTERVAL >= System.nanoTime()) {
			super.onBackPressed();
		} else {
			mBackPressed = System.nanoTime();
		}
    }

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyLongPress.keyCode=" + keyCode + ", event=" + event);
		if (keyCode == KeyEvent.KEYCODE_BACK
			&& event.getAction() == KeyEvent.ACTION_DOWN) {
			super.onBackPressed();
			return true;
		}
		return false;
	}
	
    private void injectCSS(WebView webview) {
        try {
            String css = "*, :after, :before {background-color: #161a1e !important; color: #61615f !important; border-color: #212a32 !important; background-image:none !important; outline-color: #161a1e !important; z-index: 1 !important} " +
                    "svg, img {filter: grayscale(100%) brightness(50%) !important; -webkit-filter: grayscale(100%) brightness(50%) !important} " +
                    "input {background-color: black !important;}" +
                    "select, option, textarea, button, input {color:#aaa !important; background-color: black !important; border:1px solid #212a32 !important}" +
                    "a, a * {text-decoration: none !important; color:#32658b !important}" +
                    "a:visited, a:visited * {color: #783b78 !important}" +
                    "* {max-width: 100vw !important} pre {white-space: pre-wrap !important}";
/*
            String cssDolphin = "*,:before,:after,html *{color:#61615f!important;-webkit-border-image:none!important;border-image:none!important;background:none!important;background-image:none!important;box-shadow:none!important;text-shadow:none!important;border-color:#212a32!important}\n" +
                    "\n" +
                    "body{background-color:#000000!important}\n" +
                    "html a,html a *{text-decoration:none!important;color:#394c65!important}\n" +
                    "html a:hover,html a:hover *{color:#394c65!important;background:#1b1e23!important}\n" +
                    "html a:visited,html a:visited *,html a:active,html a:active *{color:#58325b!important}\n" +
                    "html select,html option,html textarea,html button{color:#aaa!important;border:1px solid #212a32!important;background:#161a1e!important;border-color:#212a32!important;border-style:solid}\n" +
                    "html select:hover,html option:hover,html button:hover,html textarea:hover,html select:focus,html option:focus,html button:focus,html textarea:focus{color:#bbb!important;background:#161a1e!important;border-color:#777 #999 #999 #777 !important}\n" +
                    "html input,html input[type=text],html input[type=search],html input[type=password]{color:#4e4e4e!important;background-color:#161a1e!important;box-shadow:1px 0 4px rgba(16,18,23,.75) inset,0 1px 4px rgba(16,18,23,.75) inset!important;border-color:#1a1c27!important;border-style:solid!important}\n" +
                    "html input:focus,html input[type=text]:focus,html input[type=search]:focus,html input[type=password]:focus{color:#bbb!important;outline:none!important;background:#161a1e!important;border-color:#1a3973}\n" +
                    "html input:hover,html select:hover,html option:hover,html button:hover,html textarea:hover,html input:focus,html select:focus,html option:focus,html button:focus,html textarea:focus{color:#bbb!important;background:#093681!important;opacity:0.4!important;border-color:#777 #999 #999 #777 !important}\n" +
                    "html input[type=button],html input[type=submit],html input[type=reset],html input[type=image]{color:#4e4e4e!important;border-color:#888 #666 #666 #888 !important}\n" +
                    "html input[type=button],html input[type=submit],html input[type=reset]{border:1px solid #212a32!important;background-image:0 color-stop(1,#181a23))!important}\n" +
                    "html input[type=button]:hover,html input[type=submit]:hover,html input[type=reset]:hover,html input[type=image]:hover{border-color:#777 #999 #999 #777 !important}\n" +
                    "html input[type=button]:hover,html input[type=submit]:hover,html input[type=reset]:hover{border:1px solid #666!important;background-image:0 color-stop(1,#262939))!important}\n" +
                    "html img,html svg{opacity:.5!important;border-color:#111!important}\n" +
                    "html ::-webkit-input-placeholder{color:#4e4e4e!important}\n";
*/
            final String styleElementId = "night_mode_style_4398357";   // should be unique
            String js;
            if (isNightMode) {
                js = "if (document.head) {" +
                        "if (!window.night_mode_id_list) night_mode_id_list = new Set();" +
                        "var newset = new Set();" +
                        "   for (var n of document.querySelectorAll(':not(a)')) { " +
                        "     if (n.closest('a') != null) continue;" +
                        "     if (!n.id) n.id = 'night_mode_id_' + (night_mode_id_list.size + newset.size);" +
                        "     if (!night_mode_id_list.has(n.id)) newset.add(n.id); " +
                        "   }" +
                        "for (var item of newset) night_mode_id_list.add(item);" +
                        "var style = document.getElementById('" + styleElementId + "');" +
                        "if (!style) {" +
                        "   style = document.createElement('style');" +
                        "   style.id = '" + styleElementId + "';" +
                        "   style.type = 'text/css';" +
                        "   style.innerHTML = '" + css + "';" +
                        "   document.head.appendChild(style);" +
                        "}" +
                        "   var css2 = ' ';" +
                        "   for (var nid of newset) css2 += ('#' + nid + '#' + nid + ',');" +
                        "   css2 += '#nonexistent {background-color: #161a1e !important; color: #61615f !important; border-color: #212a32 !important; background-image:none !important; outline-color: #161a1e !important; z-index: 1 !important}';" +
                        "   style.innerHTML += css2;" +
                        "}" +
                        "var iframes = document.getElementsByTagName('iframe');" +
                        "for (var i = 0; i < iframes.length; i++) {" +
                        "   var fr = iframes[i];" +
                        "   var style = fr.contentWindow.document.createElement('style');" +
                        "   style.id = '" + styleElementId + "';" +
                        "   style.type = 'text/css';" +
                        "   style.innerHTML = '" + css + "';" +
                        "   fr.contentDocument.head.appendChild(style);" +
                        "}";
            } else {
                js = "if (document.head && document.getElementById('" + styleElementId + "')) {" +
                        "   var style = document.getElementById('" + styleElementId + "');" +
                        "   document.head.removeChild(style);" +
                        "   window.night_mode_id_list = undefined;" +
                        "}" +
                        "var iframes = document.getElementsByTagName('iframe');" +
                        "for (var i = 0; i < iframes.length; i++) {" +
                        "   var fr = iframes[i];" +
                        "   var style = fr.contentWindow.document.getElementById('" + styleElementId + "');" +
                        "   fr.contentDocument.head.removeChild(style);" +
                        "}";
            }
            webview.evaluateJavascript("javascript:(function() {" + js + "})()", null);
            if (!getCurrentTab().isDesktopUA) {
                webview.evaluateJavascript("javascript:document.querySelector('meta[name=viewport]').content='width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=1';", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasOrRequestPermission(final String permission, String explanation, final int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            return true;
        }
        if (explanation != null && shouldShowRequestPermissionRationale(permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage(explanation)
				.setPositiveButton("OK", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						requestPermissions(new String[] {permission}, requestCode);}})
                    .show();
            return false;
        }
        requestPermissions(new String[] {permission}, requestCode);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        switch (requestCode) {
            case PERMISSION_REQUEST_EXPORT_BOOKMARKS:
                exportBookmarks();
                break;
            case PERMISSION_REQUEST_IMPORT_BOOKMARKS:
                importBookmarks();
                break;
			case PERMISSION_REQUEST_READ_EXTERNAL:
				newBackgroundTab(urlIntent, false);
				switchToTab(tabs.size() - 1);
				break;
        }
    }

    private void setToolbarButtonActions(final View view, final Runnable click, final Runnable longClick, final Runnable swipeUp) {
        if (click != null) {
            view.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						click.run();}});
        }
        if (longClick != null) {
            view.setOnLongClickListener(new OnLongClickListener() {
					public boolean onLongClick(View v) {
						longClick.run();
						return true;
					}});
        }
        if (swipeUp != null) {
            final GestureDetector gestureDetector = new GestureDetector(this, new MyGestureDetector(this, null, null, swipeUp, null));
            //noinspection AndroidLintClickableViewAccessibility
            view.setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						return gestureDetector.onTouchEvent(event);}});
        }
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final double FORBIDDEN_ZONE_MIN = Math.PI / 4 - Math.PI / 12;
        private static final double FORBIDDEN_ZONE_MAX = Math.PI / 4 + Math.PI / 12;
        private static final int MIN_VELOCITY_DP = 80;  // 0.5 inch/sec
        private static final int MIN_DISTANCE_DP = 80;  // 0.5 inch
        private final float MIN_VELOCITY_PX;
        private final float MIN_DISTANCE_PX;
		final Runnable swipeLeft;
		final Runnable swipeRight;
		final Runnable swipeUp;
		final Runnable swipeDown;

		MyGestureDetector(Context context, final Runnable swipeLeft, final Runnable swipeRight, final Runnable swipeUp, final Runnable swipeDown) {
            float density = context.getResources().getDisplayMetrics().density;
            MIN_VELOCITY_PX = MIN_VELOCITY_DP * density;
            MIN_DISTANCE_PX = MIN_DISTANCE_DP * density;
			this.swipeLeft = swipeLeft;
			this.swipeRight = swipeRight;
			this.swipeUp = swipeUp;
			this.swipeDown = swipeDown;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float velocitySquared = velocityX * velocityX + velocityY * velocityY;
            if (velocitySquared < MIN_VELOCITY_PX * MIN_VELOCITY_PX) {
                // too slow
                return false;
            }

            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();

            if (Math.abs(deltaX) < MIN_DISTANCE_PX && Math.abs(deltaY) < MIN_DISTANCE_PX) {
                // small movement
                return false;
            }

            double angle = Math.atan2(Math.abs(deltaY), Math.abs(deltaX));
            if (angle > FORBIDDEN_ZONE_MIN && angle < FORBIDDEN_ZONE_MAX) {
                return false;
            }

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX > 0) {
                    return onFlingRight();
                } else {
                    return onFlingLeft();
                }
            } else {
                if (deltaY > 0) {
                    return onFlingDown();
                } else {
                    return onFlingUp();
                }
            }
        }

        boolean onFlingRight() {
            if (swipeRight != null) {
				swipeRight.run();
			}
            return true;
        }

        boolean onFlingLeft() {
			if (swipeLeft != null) {
				swipeLeft.run();
			}
            return true;
        }

        boolean onFlingUp() {
            if (swipeUp != null) {
				swipeUp.run();
			}
            return true;
        }

        boolean onFlingDown() {
            if (swipeDown != null) {
				swipeDown.run();
			}
            return true;
        }
    }
	private class LogArrayAdapter extends ArrayAdapter<String> implements View.OnClickListener {
		private CharSequence recentConstraint;
		class Holder {
			final TextView textView;
			String item;
			Holder(View convertView) {
				textView = (TextView) convertView.findViewById(android.R.id.text1);
				textView.setOnClickListener(LogArrayAdapter.this);
				convertView.setOnClickListener(LogArrayAdapter.this);
				textView.setTag(this);
				convertView.setTag(this);
				textView.setTextColor(textColor);
				textView.setBackgroundColor(backgroundColor);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13.f);
			}
		}
		public LogArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource);
		}
		@Override
		public void onClick(View p1) {
			Holder tag = (Holder) p1.getTag();
			final String item = (tag).item;
			newForegroundTab(item, getCurrentTab().isIncognito);
		}
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Holder holder;
			if (convertView == null) {
				convertView = super.getView(position, convertView, parent);//getLayoutInflater().inflate(R.layout.list_item, parent, false);
				holder = new Holder(convertView);
			} else {
				holder = (Holder) convertView.getTag();
			}
			final TextView textView = holder.textView;
			final String item = getItem(position);
            assert item != null;
			holder.item = item;
			textView.setText(item);
			return convertView;
        }
		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					LogArrayAdapter.this.clear();
					LogArrayAdapter.this.addAll((ArrayList<String>) results.values);
					if (results.count == 0 ) {
						LogArrayAdapter.this.notifyDataSetInvalidated();
					} else {
						LogArrayAdapter.this.notifyDataSetChanged();
					}
				}
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					if (constraint != null && constraint.length() == 0) {
						constraint = recentConstraint;
					}
					recentConstraint = constraint;
					FilterResults results = new FilterResults();
					ArrayList<String> FilteredArrayNames = new ArrayList<String>();
					if (constraint == null) {
						FilteredArrayNames.addAll(getCurrentTab().requestsLog);
					} else {
						// perform your search here using the searchConstraint String.
						constraint = constraint.toString().toLowerCase();
						Pattern pattern = Pattern.compile(constraint.toString(), Pattern.CASE_INSENSITIVE);
						Tab currentTab = getCurrentTab();
						for (String s : currentTab.requestsLog) {
							if (pattern.matcher(s.toLowerCase()).matches())  {
								FilteredArrayNames.add(s);
							}
						}
					}
					results.count = FilteredArrayNames.size();
					results.values = FilteredArrayNames;
					//Log.e("VALUES", results.values.toString());
					return results;
				}
			};
			return filter;
		}
    }
    private static class ArrayAdapterWithCurrentItem<T> extends ArrayAdapter<T> {
        int currentIndex;

        ArrayAdapterWithCurrentItem(@NonNull Context context, int resource, @NonNull T[] objects, int currentIndex) {
            super(context, resource, objects);
            this.currentIndex = currentIndex;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            int icon = position == currentIndex ? android.R.drawable.ic_menu_mylocation : R.drawable.empty;
            Drawable d = getContext().getResources().getDrawable(icon, null);
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getContext().getResources().getDisplayMetrics());
            d.setBounds(0, 0, size, size);
            textView.setCompoundDrawablesRelative(d, null, null, null);
            textView.setCompoundDrawablePadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
            return view;
        }
    }

//    static class HistoryArrayAdapter extends ArrayAdapter<HistoryData> implements View.OnClickListener {
//		DisplayMetrics m;
//		int size;
//		class Holder {
//			final TextView textView;
//			HistoryData item;
//			Holder(View convertView) {
//				textView = (TextView) convertView.findViewById(android.R.id.text1);
//				textView.setOnClickListener(HistoryArrayAdapter.this);
//				convertView.setOnClickListener(HistoryArrayAdapter.this);
//				textView.setTag(this);
//				convertView.setTag(this);
//			}
//		}
//		@Override
//		public void onClick(View p1) {
//			Holder tag = (Holder) p1.getTag();
//			final HistoryData item = (tag).item;
//			Drawable right = null;
//            
//
//            tag.textView.setCompoundDrawables(tag.textView.getCompoundDrawables()[0], null, right, null);
//            tag.textView.setCompoundDrawablePadding(
//				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, m));
//			HistoryArrayAdapter.this.notifyDataSetChanged();
//		}
//
//        HistoryArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MenuAction> objects) {
//            super(context, resource, objects);
//			m = getContext().getResources().getDisplayMetrics();
//			size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, m);
//		}
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            final Holder holder;
//			if (convertView == null) {
//				convertView = super.getView(position, convertView, parent);//getLayoutInflater().inflate(R.layout.list_item, parent, false);
//				holder = new Holder(convertView);
//			} else {
//				holder = (Holder) convertView.getTag();
//			}
//
//			final TextView textView = holder.textView;
//			final HistoryData item = getItem(position);
//            assert item != null;
//			holder.item = item;
//			textView.setText(item.title);
////View view = super.getView(position, convertView, parent);
////			TextView textView = (TextView) view.findViewById(android.R.id.text1);
//
//            
//            return convertView;
//        }
//    }

    private static class MenuActionArrayAdapter extends ArrayAdapter<MenuAction> implements View.OnClickListener {
		DisplayMetrics m;
		int size;
		class Holder {
			final TextView textView;
			MenuAction item;
			Holder(View convertView) {
				textView = (TextView) convertView.findViewById(android.R.id.text1);
				textView.setOnClickListener(MenuActionArrayAdapter.this);
				convertView.setOnClickListener(MenuActionArrayAdapter.this);
				textView.setTag(this);
				convertView.setTag(this);
			}
		}
		@Override
		public void onClick(View p1) {
			Holder tag = (Holder) p1.getTag();
			final MenuAction item = (tag).item;
			item.action.run();
			Drawable right = null;
            if (item.getState != null) {
                int icon = item.getState.getAsBoolean() ? android.R.drawable.checkbox_on_background :
					android.R.drawable.checkbox_off_background;
                right = getContext().getResources().getDrawable(icon, null);
                right.setBounds(0, 0, size, size);
            }

            tag.textView.setCompoundDrawables(tag.textView.getCompoundDrawables()[0], null, right, null);
            tag.textView.setCompoundDrawablePadding(
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, m));
			MenuActionArrayAdapter.this.notifyDataSetChanged();
		}
		
        MenuActionArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MenuAction> objects) {
            super(context, resource, objects);
			m = getContext().getResources().getDisplayMetrics();
			size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, m);
		}

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Holder holder;
			if (convertView == null) {
				convertView = super.getView(position, convertView, parent);//getLayoutInflater().inflate(R.layout.list_item, parent, false);
				holder = new Holder(convertView);
			} else {
				holder = (Holder) convertView.getTag();
			}

			final TextView textView = holder.textView;
			final MenuAction item = getItem(position);
            assert item != null;
			holder.item = item;
			textView.setText(item.title);
//View view = super.getView(position, convertView, parent);
//			TextView textView = (TextView) view.findViewById(android.R.id.text1);
            
            Drawable left = getContext().getResources().getDrawable(item.icon != 0 ? item.icon : R.drawable.empty, null);
            left.setBounds(0, 0, size, size);
            left.setTint(0xffeeeeee);

            Drawable right = null;
            if (item.getState != null) {
                int icon = item.getState.getAsBoolean() ? android.R.drawable.checkbox_on_background :
                        android.R.drawable.checkbox_off_background;
                right = getContext().getResources().getDrawable(icon, null);
                right.setBounds(0, 0, size, size);
            }

            textView.setCompoundDrawables(left, null, right, null);
            textView.setCompoundDrawablePadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, m));

            return convertView;
        }
    }

    private static class SearchAutocompleteAdapter extends BaseAdapter implements Filterable {

        interface OnSearchCommitListener {
            void onSearchCommit(String text);
        }

        private final Context mContext;
        private final OnSearchCommitListener commitListener;
        private List<String> completions = new ArrayList<>();

        SearchAutocompleteAdapter(Context context, OnSearchCommitListener commitListener) {
            mContext = context;
            this.commitListener = commitListener;
        }

        @Override
        public int getCount() {
            return completions.size();
        }

        @Override
        public Object getItem(int position) {
            return completions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        @SuppressWarnings("ConstantConditions")
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            }
            TextView v = (TextView) convertView.findViewById(android.R.id.text1);
            v.setText(completions.get(position));
            Drawable right = mContext.getResources().getDrawable(R.drawable.commit_search, null);
            final int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mContext.getResources().getDisplayMetrics());
            right.setBounds(0, 0, size, size);
            v.setCompoundDrawables(null, null, right, null);
            //noinspection AndroidLintClickableViewAccessibility
            v.setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View v1, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) {
                    return false;
                }
                TextView t = (TextView) v1;
                if (event.getX() > t.getWidth() - t.getCompoundPaddingRight()) {
                    commitListener.onSearchCommit(getItem(position).toString());
                    return true;
                }
                return false;
            }});
            //noinspection AndroidLintClickableViewAccessibility
            parent.setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View dropdown, MotionEvent event) {
                if (event.getX() > dropdown.getWidth() - size * 2) {
                    return true;
                }
                return false;
            }});
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    // Invoked on a worker thread
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        List<String> results = getCompletions(constraint.toString());
                        filterResults.values = results;
                        filterResults.count = results.size();
                    }
                    return filterResults;
                }

                @Override
                @SuppressWarnings("unchecked")
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        completions = (List<String>) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
        }

        // Runs on a worker thread
        private List<String> getCompletions(String text) {
            int total = 0;
            byte[] data = new byte[16384];
            try {
                URL url = new URL(URLUtil.composeSearchUrl(text, searchCompleteUrl, "%s"));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    while (total <= data.length) {
                        int count = in.read(data, total, data.length - total);
                        if (count == -1) {
                            break;
                        }
                        total += count;
                    }
                    if (total == data.length) {
                        // overflow
                        return new ArrayList<>();
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                // Swallow exception and return empty list
                return new ArrayList<>();
            }

            // Result looks like:
            // [ "original query", ["completion1", "completion2", ...], ...]

            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(new String(data, StandardCharsets.UTF_8));
            } catch (JSONException e) {
                return new ArrayList<>();
            }
            jsonArray = jsonArray.optJSONArray(1);
            if (jsonArray == null) {
                return new ArrayList<>();
            }
            final int MAX_RESULTS = 10;
            List<String> result = new ArrayList<>(Math.min(jsonArray.length(), MAX_RESULTS));
            for (int i = 0; i < jsonArray.length() && result.size() < MAX_RESULTS; i++) {
                String s = jsonArray.optString(i);
                if (s != null && !s.isEmpty()) {
                    result.add(s);
                }
            }
            return result;
        }
    }
}
