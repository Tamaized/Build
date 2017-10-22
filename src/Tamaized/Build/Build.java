package Tamaized.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Build {

	public static void main(String[] args) {
		System.out.println("Starting");
		if (args.length > 1) {
			System.out.println("Looking for file: " + args[0]);
			File file = new File(args[0]);
			if (file.exists()) {
				System.out.println("File found! Looking for the variable: " + args[1]);
				int varLen = args[1].length();
				int value = -1;
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = br.readLine();
					while (line != null) {
						if (line.length() >= varLen && line.substring(0, varLen).equals(args[1])) {
							String test = line.substring(varLen + 1);
							System.out.println(test);
							value = Integer.parseInt(test);
							break;
						}
						line = br.readLine();
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					System.out.println("The Variable does not have a set positive int value; Stopping");
					System.exit(0);
				}
				if (value > -1) {
					value++;
					System.out.println("Variable found! Increasing to: " + value);
					try {
						List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
						for (int i = 0; i < fileContent.size(); i++)
							if (fileContent.get(i).length() >= varLen && fileContent.get(i).substring(0, varLen).equals(args[1])) {
								fileContent.set(i, fileContent.get(i).substring(0, varLen + 1).concat(String.valueOf(value)));
								break;
							}
						Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
						System.out.println("Finished!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					System.out.println("Could not find the variable");
			} else
				System.out.println("Could not find file");
		} else
			System.out.println("Not enough Arguments");
	}
}
