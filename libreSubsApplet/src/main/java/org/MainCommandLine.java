package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

import org.subtitleDownloadLogic.DroppedFilesProcessor;
import org.subtitleDownloadLogic.utils.SubtitleResourceResolver;

public class MainCommandLine {

	private static int PORT = 15154;
	
	public static void main(final String[] args) {
		new MainCommandLine(args);
	}
	
	private OutputListener outputListener;
	
	//TODO: refactor all this mess
	
	public MainCommandLine(final String[] args) {
		if(args.length != 1){
			System.out.println("You should give one file or directory path as an argument.");
			return;
		}
		new Thread(){
			@Override
			public void run() {
				try {
					startListening();
				}catch(final BindException bindException){
					Socket socket;
					try {
						socket = new Socket("127.0.0.1", PORT);
						writeLine(args[0],socket);
						System.exit(0);
					} catch (final UnknownHostException e) {
						throw new RuntimeException(e);
					} catch (final IOException e) {
						throw new RuntimeException(e);
					}
				}catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		}.start();
		
		outputListener = new WindowOutputListener();
		processFilePath(args[0]);
	}

	private void startListening() throws IOException {
		final ServerSocket serverSocket = new ServerSocket(PORT);
		final boolean isRunning = true;
		while(isRunning){
				final Socket socket = serverSocket.accept();
				new Thread(){
					@Override
					public void run() {						
						while(isRunning){
							try {
								readCommand(socket);
							} catch (final IOException ioe){
								System.out.println(ioe.getStackTrace());
								return;
							}
						}
					};
				}.start();
		}
	}

	private void readCommand(final Socket socket) throws IOException {
		try {
			final String fileOrDirPath = readLine(socket);
			if (fileOrDirPath == null)
				return;	
			processFilePath(fileOrDirPath);
		} catch (final SocketException ex){
			//do nothing
		}
	}
	
	private void writeLine(final String command, final Socket socket) throws IOException {
		final OutputStream outputStream = socket.getOutputStream();
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		writer.write(command + "\n");
		writer.flush();
	}
	
	private String readLine(final Socket socket) throws IOException {
		final InputStream inputStream = socket.getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		return reader.readLine();
	}

	private void processFilePath(final String fileOrDirPath) {
		final File fileOrDir = new File(fileOrDirPath);
		final ArrayList<File> fileAndDirList = new ArrayList<File>();
		fileAndDirList.add(fileOrDir);
		if(fileOrDir.exists()){
			fileAndDirList.add(fileOrDir);
		}else{
			outputListener.error("Caminho inválido ou inexistente: "+fileOrDir);
			return;
		}
		final DroppedFilesProcessor droppedFilesProcessor = getDroppedFilesProcessor();
		droppedFilesProcessor.droppedFiles(fileAndDirList);
	}

	private DroppedFilesProcessor getDroppedFilesProcessor() {
		final String downloadUrl = SiteAdresses.getDownloadurl();
		final String uploadUrl = SiteAdresses.getUploadurl();
		final SubtitleResourceResolver srtSource = new SubtitleResourceResolver(downloadUrl, uploadUrl);
		final String defaulLocale = Locale.getDefault().toString();
		final DroppedFilesProcessor droppedFilesProcessor = new DroppedFilesProcessor(srtSource, outputListener, defaulLocale);
		return droppedFilesProcessor;
	}
}
