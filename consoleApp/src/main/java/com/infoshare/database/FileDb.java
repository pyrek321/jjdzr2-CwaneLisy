package com.infoshare.database;

import com.infoshare.domain.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileDb implements DB {
    private static final String VOLUNTEER_DB_FILE_NAME = "Volunteer.csv";
    private static final String REQUEST_DB_FILE = "NeedRequest.csv";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public FileDb() {
        if (!Files.exists(Paths.get(VOLUNTEER_DB_FILE_NAME))) {
            try {
                Files.createFile(Paths.get(VOLUNTEER_DB_FILE_NAME));
            } catch (IOException e) {
                System.out.println("Plik" + VOLUNTEER_DB_FILE_NAME + " nie moze zostac stworzony");
            }
        }
        if (!Files.exists(Paths.get(REQUEST_DB_FILE))) {
            try {
                Files.createFile(Paths.get(REQUEST_DB_FILE));
            } catch (IOException e) {
                System.out.println("Plik" + REQUEST_DB_FILE + " nie moze zostac stworzony");
            }
        }
    }

    @Override
    public void saveVolunteer(Volunteer volunteer) throws IOException {

        if (getVolunteer(volunteer.getEmail()) == null) {
            FileWriter fileWriter = new FileWriter(VOLUNTEER_DB_FILE_NAME, true);
            fileWriter.write(volunteer.getName() + "," + volunteer.getLocation() + "," + volunteer.getEmail() + ","
                    + volunteer.getPhone() + "," + volunteer.getTypeOfHelp() + "," + volunteer.isAvailable() + "," +
                  volunteer.getID() + "\n");
            fileWriter.close();
        } else {
            List<Volunteer> allVounteers = getVolunteers();
            int index = -1;
            for (int i = 0; i < allVounteers.size(); i++) {
                Volunteer v = allVounteers.get(i);
                if (v.getEmail().equals(volunteer.getEmail())) {
                    index = i;
                }
            }
            allVounteers.set(index, volunteer);
            FileWriter writer = new FileWriter(VOLUNTEER_DB_FILE_NAME, false);
            for (Volunteer v : allVounteers) {
                writer.write(v.getName() + "," + v.getLocation() + "," + v.getEmail() + ","
                        + v.getPhone() + "," + v.getTypeOfHelp() + "," + v.isAvailable() + "," + v.getID() + "\n");
            }
            writer.close();
        }
    }

    @Override
    public void saveNeedRequest(NeedRequest needRequest) throws IOException {
        FileWriter fileWriter = new FileWriter(REQUEST_DB_FILE, true);
        PersonInNeed person = needRequest.getPersonInNeed();
        fileWriter.write(needRequest.getTypeOfHelp() + "," + needRequest.getHelpStatus() + ","
                + df.format(needRequest.getStatusChange()) + "," +person.getName() + "," + person.getLocation()
                + "," + person.getPhone() + "," +  UUID.randomUUID() + "\n");
        fileWriter.close();
    }

    @Override
    public List<NeedRequest> getNeedRequests() throws FileNotFoundException, ParseException {
        List<NeedRequest> result = new ArrayList<>();

        Scanner scanner = new Scanner(new File(REQUEST_DB_FILE));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splited = line.split(",");
            PersonInNeed person = new PersonInNeed(splited[3], splited[4], splited[5]);
            NeedRequest needRequest = new NeedRequest(TypeOfHelp.valueOf(splited[0]), HelpStatuses.valueOf(splited[1])
                    , df.parse(splited[2]),person);
            result.add(needRequest);
        }
        return result;
    }

    @Override
    public List<Volunteer> getVolunteers() throws FileNotFoundException {
        List<Volunteer> result = new ArrayList<>();
        Scanner scanner = new Scanner(new File(VOLUNTEER_DB_FILE_NAME));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] volunteerAtributes = line.split(",");
            result.add(new Volunteer(volunteerAtributes[0], volunteerAtributes[1], volunteerAtributes[2],
                    volunteerAtributes[3], TypeOfHelp.valueOf(volunteerAtributes[4]),
                    Boolean.parseBoolean(volunteerAtributes[5]),UUID.randomUUID()));
        }
        return result;
    }

    public Volunteer getVolunteer(String email) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(VOLUNTEER_DB_FILE_NAME));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] volunteerAtributes = line.split(",");
            if (volunteerAtributes.length >= 6 && volunteerAtributes[2].equals(email)) {
                return new Volunteer(volunteerAtributes[0], volunteerAtributes[1], volunteerAtributes[2],
                        volunteerAtributes[3], TypeOfHelp.valueOf(volunteerAtributes[4]),
                        Boolean.parseBoolean(volunteerAtributes[5]),UUID.randomUUID());
            }
        }
        return null;
    }
}
