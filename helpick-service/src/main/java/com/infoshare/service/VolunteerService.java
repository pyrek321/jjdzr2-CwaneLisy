package com.infoshare.service;

import com.infoshare.database.DB;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.domain.Volunteer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VolunteerService {

    DB db;

    @Autowired
    public VolunteerService(DB db) {
        this.db = db;
    }

    public List<Volunteer> getVolunteerFilteredList(String city, TypeOfHelp typeOfHelp) {
        return db.getVolunteers().stream()
            .filter(Volunteer::isAvailable)
            .filter(v -> v.getLocation().equalsIgnoreCase(city))
            .filter(v -> v.getTypeOfHelp().equals(typeOfHelp))
            .collect(Collectors.toList());
    }

    public Volunteer searchForVolunteer(String email) {
        return db.getVolunteer(email);
    }

    public Optional<Volunteer>getVolunteerById(UUID uuid){
      return db.getVolunteers().stream()
          .filter(v->v.getUuid().equals(uuid))
          .findAny();
    }

    public boolean updateAvailability(Volunteer volunteer) {
        if (volunteer != null) {
            volunteer.setAvailable(!volunteer.isAvailable());
            db.saveVolunteer(volunteer);
            return true;
        } else {
            return false;
        }
    }

    public boolean registerNewVolunteer(String name, String location, String email, String phone, TypeOfHelp typeOfHelp,
        boolean availability) {
        Volunteer newVolunteer = new Volunteer(name, location, email, phone, typeOfHelp, availability, UUID.randomUUID());
        if (db.getVolunteer(newVolunteer.getEmail()) == null) {
            db.saveVolunteer(newVolunteer);
            return true;
        } else {
            return false;
        }
    }
    public List<TypeOfHelp> getTypesOfHelp() {
        return Arrays.asList(TypeOfHelp.values());
    }

    public List<Volunteer> getAllVolunteers() {
        return db.getVolunteers();
    }

    public boolean editVolunteerData(String name, String location, String email, String phone, TypeOfHelp typeOfHelp,
                                     boolean availability, UUID uuid) {
        Optional<Volunteer> volunteer = db.getVolunteer(uuid);
        if (volunteer.isPresent()) {
            Volunteer volunteerToEdit = volunteer.get();
            volunteerToEdit.setName(name);
            volunteerToEdit.setLocation(location);
            volunteerToEdit.setPhone(phone);
            volunteerToEdit.setTypeOfHelp(typeOfHelp);
            volunteerToEdit.setAvailable(availability);
            volunteerToEdit.setEmail(email);
            db.saveVolunteerWithUuid(volunteerToEdit);
            return true;
        } else {
            return false;
        }
    }
}
