package shaffer.j;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommandLineShell {
	// get currect dir
	static File userDirectory = new File(".").getAbsoluteFile();
	static String current1 = userDirectory.toString();
	static Path current = Paths.get(current1);

	public static void main(String[] args) throws IOException {
		System.out.println("Wecome to shell");
		getRequest(current);
	}

	// method to get user input and navigate based on input
	private static void getRequest(Path current) throws IOException {

		String request = "";
		Scanner input = new Scanner(System.in);

		do {
			System.out.print("prompt> ");
			request = input.nextLine();

			if (request.trim() == null || request.trim().equals("")) {
				System.out.println("Unknown command " + "\"" + request + "\"");
				helpMENU();
			} else if (request.equals("exit") == true) {
				System.out.println("Good Bye");
				System.exit(0);
			} else if (request.equals("help") == true) {
				helpMENU();
			} else if (request.startsWith("cd") == true) {
				initialCD(request, current);
			} else if (request.startsWith("dir ") == true || request.equals("dir")) {
				initialDIR(current);
			} else if (request.startsWith("show") == true) {
				initialSHOW(request, current);
			} else {
				System.out.println("Unknown command " + request);
				helpMENU();
			}
		} while (request != "exit");
	}

	// display menu than back to get user input
	private static void helpMENU() throws IOException {

		System.out.printf("COMMANDS: %n%s%n%s%n%s%n%s%n%s%n", 
				"help         Show list of commands",
				"dir          List contents of current directory", 
				"cd [dir]     Change to directory",
				"show [files] Show contents of file", 
				"exit         Exit the shell");
		getRequest(current);
	}

	// display current dir method
	private static void initialDIR(Path current) throws IOException {

		File currentDirectory = new File(current + "/.").getAbsoluteFile();
		System.out.println("Directory of " + "\"" + currentDirectory + "\"");
		
		File[] filesList = currentDirectory.listFiles();
		for (File file : filesList) {
			if (file.isFile()) {
					System.out.println("\t\t" + file.length() + " " + file.getName());
			}
			if (file.isDirectory()) {
					System.out.println("d\t\t" + file.getName());
			}
		}
		getRequest(current);
	}

	// change dir
	private static void initialCD(String input, Path current) throws IOException {

		try {
			String changeTo = input.substring(3);
			Path p = Paths.get(changeTo);
			if (!p.isAbsolute()) {
				p = Paths.get(current.toString(), p.getFileName().toString());
			}
			if (!Files.exists(p)) {
				System.out.printf("ERROR: Path does not exist: \"%s\"\n", p.toAbsolutePath());
			} else if (!Files.isDirectory(p)) {
				System.out.printf("ERROR: Path is not a directory: \"%s\"\n", p.toRealPath());
			} else {
				current = p;
				System.out.printf("SUCCESS: \"%s\"\n", current.toRealPath());
			}
		} catch (NullPointerException e) {
			System.out.println("Error: Path is not a directory");
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("Error: path not given");
		}

		current1 = current.toString();
		getRequest(current);
	}

	// show contents of a file
	private static void initialSHOW(String input, Path current) throws IOException {
		
		try {
			String output;
			String subInput = "";
			subInput = input.substring(5);
	
			Path path = Paths.get(current + "/" + subInput);
			if (Files.exists(path)) {
				try {
					System.out.println("SHOW: " + "\"" + path + "\"");
					BufferedReader in = new BufferedReader(new FileReader(path.toString()));
					while ((output = in.readLine()) != null) {
						System.out.println(output);
					}
				} catch (FileNotFoundException e) {
					System.out.println("Error: file does not exist: " + "\"" + path + "\"");
				}
			} else {
				System.out.println("Error: path is not a file: " + "\"" + path + "\"");
			}
		} catch (NullPointerException e) {
			System.out.println("Error: path is not a file");
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("Error: path not given");
		}
		
		getRequest(current);
	}
}
