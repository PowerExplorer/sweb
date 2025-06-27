package net.gnu.util;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import java.io.InputStream;
import java.io.IOException;

public class SevenZInputStream extends InputStream {
	private static final String TAG = "SevenZInputStream";

	private final SevenZFile sevenZFile;
	private final int length;
	private final String fileName;
	private final String entryName;
	//private final long nano;
    private int pos = 0;
	private boolean closed = false;

	public SevenZInputStream(final SevenZFile sevenZFile, final int length, final String entryName) {
		ExceptionLogger.d(TAG, "length " + Util.nf.format(length) + ", sevenZFile " + sevenZFile +", entryName " + entryName);
		this.sevenZFile = sevenZFile;
		this.length = length;
		this.fileName = sevenZFile.getDefaultName();
		this.entryName = entryName;
		//nano = System.nanoTime();
	}

	@Override
    public int read() throws IOException {
		ensureOpen();
		final int read = sevenZFile.read();
		if (read != -1) 
			pos++;
		//ExceptionLogger.d(TAG, "read1 pos " + pos + ", " + read);
		return read;
	}

	@Override
    public int read(final byte[] barr) throws IOException {
		ensureOpen();
		int remaining = length - pos;
		int readLen = Math.min(barr.length, remaining);
		final int read = sevenZFile.read(barr, 0, readLen);
		if (read > 0) 
			pos += read;
		//ExceptionLogger.d(TAG, "read2 pos " + pos + ", " + read);
		return read;
	}

	@Override
    public int read(final byte[] barr, final int off, final int len) throws IOException {
		ensureOpen();
		int remaining = length - pos;
		int readLen = Math.min(len, remaining);
		final int read = sevenZFile.read(barr, off, readLen);
		if (read > 0) 
			pos += read;
		//ExceptionLogger.d(TAG, "read3 pos " + pos + ", " + read);
		return read;
	}

	@Override
    public long skip(final long n) throws IOException {
		ExceptionLogger.d(TAG, "skip   " + Util.nf.format(n));
		ensureOpen();
		long k = length - pos;
        final long possibleSkipRange = (n < 0) ? 0 : (n < k) ? n : k;
        
		k = possibleSkipRange;
		if (k > 0) {
			final int LEN = 4096;
			final byte[] barr = new byte[LEN];
			int read = 0;
			while (k > 0 && (read = sevenZFile.read(barr, 0, (int)(k > LEN ? LEN : k))) > 0) {
				if (read > 0) 
					k -= read;
			}
			pos += (possibleSkipRange - k);
		}
		ExceptionLogger.d(TAG, "skipped " + Util.nf.format(possibleSkipRange));
		return possibleSkipRange - k;
	}

	@Override
    public int available() throws IOException {
		ensureOpen();
		//ExceptionLogger.d(TAG, "available " + (length - pos));
		return Math.max(0, length - pos);
	}

	@Override
    public void close() {
		//ExceptionLogger.d(TAG, "close pos=" + Util.nf.format(pos) + ", sevenZFile " + fileName +", entryName " + entryName + ", took " + Util.nf.format(System.nanoTime() - nano));
		//sevenZFile.close();
		closed = true;
	}

	@Override
    public synchronized void mark(final int readlimit) {
		ExceptionLogger.d(TAG, "UnsupportedOperationException mark " + readlimit);
		throw new UnsupportedOperationException();
	}

	@Override
    public synchronized void reset() {
		ExceptionLogger.d(TAG, "UnsupportedOperationException reset");
		throw new UnsupportedOperationException();
	}

	@Override
    public boolean markSupported() {
		ExceptionLogger.d(TAG, "markSupported false");
		return false;
	}

    private void ensureOpen() throws IOException {
        if (closed)
			throw new IOException("Stream closed");
    }
	
	public int getPosition() {
		return pos;
	}

	public String getEntryName() {
		return entryName;
	}

	public int getLength() {
		return length;
	}
}
