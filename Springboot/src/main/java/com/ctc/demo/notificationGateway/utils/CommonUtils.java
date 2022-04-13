package com.ctc.demo.notificationGateway.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommonUtils {
	public static void runCommand(String command, String... params) {
		ArrayList<String> commands = new ArrayList<String>();
		Process p;
		ProcessBuilder pb = null;
		StringBuffer output = new StringBuffer();

		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("win")) {

			commands.add("cmd.exe");
			commands.add("/c");
			commands.add(command);
			for (String param: params) {
				commands.add(param);
			}

			pb = new ProcessBuilder(commands.toArray(new String[0]));
			pb.redirectErrorStream(true);
		}
		try {
			p = pb.start();
			int exitCode = p.waitFor();
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			CTCLogger.info("runCommand, {0} output: \n    {1}", command, output.toString());
			CTCLogger.info("runCommand, {0} exitCode: \n    {1}", command, exitCode);
		} catch (InterruptedException e) {
			CTCLogger.error("the commands: {0} is interrupted by {1}", command, e.getMessage());
		} catch (IOException e) {
			CTCLogger.error("the commands: {0} failed, caused by {1}", command, e.getMessage());
		}
	}

}
