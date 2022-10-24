package com.example.workers.utils;

import com.example.workers.data.Worker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class JSONUtils {

    private static final String KEY_RESPONSE = "response";

    // Информация о работнике
    private static final String KEY_NAME = "f_name";
    private static final String KEY_SURNAME = "l_name";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_SPECIALTY = "specialty";
    private static final String KEY_SPECIALTY_NAME = "name";

    public static ArrayList<Worker> getWorkerFromJSON(JSONObject jsonObject) {
        ArrayList<Worker> result = new ArrayList<>();
        if (jsonObject == null) {
            return null;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESPONSE);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectWorker = jsonArray.getJSONObject(i);

                String name = jsonObjectWorker.getString(KEY_NAME).toLowerCase(Locale.ROOT);
                name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase(Locale.ROOT));

                String surname = jsonObjectWorker.getString(KEY_SURNAME).toLowerCase(Locale.ROOT);
                surname = surname.replaceFirst(surname.substring(0, 1), surname.substring(0, 1).toUpperCase(Locale.ROOT));

                String birthday = jsonObjectWorker.getString(KEY_BIRTHDAY).replaceAll("-", ".");
                String correctBirthday = JSONUtils.getCorrectBirthday(birthday);
                String age = JSONUtils.getAge(correctBirthday);

                JSONArray jsonArraySpeciality = jsonObjectWorker.getJSONArray(KEY_SPECIALTY);
                JSONObject jsonObjectSpeciality = jsonArraySpeciality.getJSONObject(0);
                String speciality = jsonObjectSpeciality.getString(KEY_SPECIALTY_NAME);
                Worker worker = new Worker(name, surname, correctBirthday, speciality, age);
                result.add(worker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCorrectBirthday(String birthday) {
        String result = birthday;
        if (birthday != null && birthday.length() > 0 && !birthday.equals("null")) {
            if (birthday.split("\\.")[0].length() > 2) {
                DateTimeFormatter dtfStart = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                DateTimeFormatter dtfEnd = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate date = LocalDate.parse(birthday, dtfStart);
                result = dtfEnd.format(date);
            }
        } else
            result = "Дата рождения не указана";

        return result;
    }

    public static String getAge(String correctBirthday) {
        String age = "Возраст не указан";
        String date = correctBirthday;
        if (date != null && date.length() > 0 && date.length() < 11) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(date, dtf);
            age = String.valueOf(Period.between(localDate, LocalDate.now()).getYears());
        }
        return age;
    }


}
