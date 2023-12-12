package com.example.collegeflight;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.collegeflight.bean.Flight;
import com.example.collegeflight.bean.Order;
import com.example.collegeflight.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user_info";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_BIRTH_DATE = "birth_date";
    public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_USER_PASSPORT_COUNTRY = "passport_country";
    public static final String COLUMN_USER_PASSPORT_NUMBER = "passport_number";
    public static final String COLUMN_USER_PASSPORT_EXPIRE_DATE = "passport_expire_date";


    public static final String TABLE_FLIGHTS = "flights";
    public static final String COLUMN_FLIGHT_ID = "flight_id";
    public static final String COLUMN_FLIGHT_DEPARTURE = "departure";
    public static final String COLUMN_FLIGHT_DESTINATION = "destination";
    public static final String COLUMN_FLIGHT_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_FLIGHT_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_FLIGHT_DURATION = "duration";
    public static final String COLUMN_FLIGHT_PRICE = "price";


    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_USER_ID = "user_id";
    public static final String COLUMN_ORDER_FLIGHT_ID = "flight_id";
    public static final String COLUMN_ORDER_DEPARTURE_CITY = "departure_city";
    public static final String COLUMN_ORDER_DESTINATION_CITY = "destination_city";
    public static final String COLUMN_ORDER_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_ORDER_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_ORDER_TOTAL_DURATION = "total_duration";
    public static final String COLUMN_ORDER_FLIGHT_DATE = "flight_date";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_FIRST_NAME + " TEXT, " +
                COLUMN_USER_LAST_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_BIRTH_DATE + " TEXT, " +
                COLUMN_USER_PHONE_NUMBER + " TEXT, " +
                COLUMN_USER_PASSPORT_COUNTRY + " TEXT, " +
                COLUMN_USER_PASSPORT_NUMBER + " TEXT, " +
                COLUMN_USER_PASSPORT_EXPIRE_DATE + " TEXT)";
        db.execSQL(createUserTableQuery);

        String createFlightsTableQuery = "CREATE TABLE " + TABLE_FLIGHTS + " (" +
                COLUMN_FLIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FLIGHT_DEPARTURE + " TEXT, " +
                COLUMN_FLIGHT_DESTINATION + " TEXT, " +
                COLUMN_FLIGHT_DEPARTURE_TIME + " TEXT, " +
                COLUMN_FLIGHT_ARRIVAL_TIME + " TEXT, " +
                COLUMN_FLIGHT_DURATION + " TEXT, " +
                COLUMN_FLIGHT_PRICE + " REAL)";
        db.execSQL(createFlightsTableQuery);

        String createOrdersTableQuery = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_USER_ID + " INTEGER, " +
                COLUMN_ORDER_FLIGHT_ID + " INTEGER, " +
                COLUMN_ORDER_DEPARTURE_CITY + " TEXT, " +
                COLUMN_ORDER_DESTINATION_CITY + " TEXT, " +
                COLUMN_ORDER_DEPARTURE_TIME + " TEXT, " +
                COLUMN_ORDER_ARRIVAL_TIME + " TEXT, " +
                COLUMN_ORDER_TOTAL_DURATION + " TEXT, " +
                COLUMN_ORDER_FLIGHT_DATE + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_ORDER_USER_ID + ") REFERENCES " + TABLE_USER + " (" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY (" + COLUMN_ORDER_FLIGHT_ID + ") REFERENCES " + TABLE_FLIGHTS + " (" + COLUMN_FLIGHT_ID + "))";

        db.execSQL(createOrdersTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public UserInfo getUserInfo(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = null;

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_BIRTH_DATE,
                COLUMN_USER_PASSPORT_COUNTRY,
                COLUMN_USER_PASSPORT_NUMBER,
                COLUMN_USER_PASSPORT_EXPIRE_DATE,
                COLUMN_USER_PHONE_NUMBER
        };

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(
                TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            userInfo = new UserInfo();
            userInfo.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            userInfo.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME)));
            userInfo.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME)));
            userInfo.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
            userInfo.setBirthDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_BIRTH_DATE)));
            userInfo.setPassportCountry(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSPORT_COUNTRY)));
            userInfo.setPassportNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSPORT_NUMBER)));
            userInfo.setPassportExpireDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSPORT_EXPIRE_DATE)));
            userInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE_NUMBER)));
        }

        cursor.close();
        db.close();

        return userInfo;
    }

    public void updateUserInfo(String userId, UserInfo updatedUserInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, updatedUserInfo.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, updatedUserInfo.getLastName());
        values.put(COLUMN_USER_EMAIL, updatedUserInfo.getEmail());
        values.put(COLUMN_USER_BIRTH_DATE, updatedUserInfo.getBirthDate());
        values.put(COLUMN_USER_PHONE_NUMBER, updatedUserInfo.getPhoneNumber());
        values.put(COLUMN_USER_PASSPORT_COUNTRY, updatedUserInfo.getPassportCountry());
        values.put(COLUMN_USER_PASSPORT_NUMBER, updatedUserInfo.getPassportNumber());
        values.put(COLUMN_USER_PASSPORT_EXPIRE_DATE, updatedUserInfo.getPassportExpireDate());

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userId};

        db.update(TABLE_USER, values, selection, selectionArgs);

        db.close();
    }

    public List<String> getAllDepartures() {
        List<String> departures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_FLIGHTS, new String[]{COLUMN_FLIGHT_DEPARTURE}, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            departures.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return departures;
    }

    public List<String> getAllDestinations() {
        List<String> destinations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_FLIGHTS, new String[]{COLUMN_FLIGHT_DESTINATION}, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            destinations.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return destinations;
    }

    public List<Flight> getFlights(String departure, String destination) {
        List<Flight> flights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_FLIGHT_DEPARTURE + " = ? AND " + COLUMN_FLIGHT_DESTINATION + " = ?";
        String[] selectionArgs = {departure, destination};

        Cursor cursor = db.query(TABLE_FLIGHTS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            Flight flight = new Flight();
            flight.setFlightId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_ID)));
            flight.setDeparture(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_DEPARTURE)));
            flight.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_DESTINATION)));
            flight.setDepartureTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_DEPARTURE_TIME)));
            flight.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_ARRIVAL_TIME)));
            flight.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_DURATION)));
            flight.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_PRICE)));
            flights.add(flight);
        }
        cursor.close();
        db.close();
        return flights;
    }
    public void purchaseFlight(Flight flight, int userId, String selectedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_USER_ID, userId);
        values.put(COLUMN_ORDER_FLIGHT_ID, flight.getFlightId());
        values.put(COLUMN_ORDER_DEPARTURE_CITY, flight.getDeparture());
        values.put(COLUMN_ORDER_DESTINATION_CITY, flight.getDestination());
        values.put(COLUMN_ORDER_DEPARTURE_TIME, flight.getDepartureTime());
        values.put(COLUMN_ORDER_ARRIVAL_TIME, flight.getArrivalTime());
        values.put(COLUMN_ORDER_TOTAL_DURATION, flight.getDuration());
        values.put(COLUMN_ORDER_FLIGHT_DATE, selectedDate);

        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }
    public void insertFlights(List<Flight> flights) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Flight flight : flights) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FLIGHT_DEPARTURE, flight.getDeparture());
            values.put(COLUMN_FLIGHT_DEPARTURE_TIME, flight.getDepartureTime());
            values.put(COLUMN_FLIGHT_DESTINATION, flight.getDestination());
            values.put(COLUMN_FLIGHT_ARRIVAL_TIME, flight.getArrivalTime());
            values.put(COLUMN_FLIGHT_DURATION, flight.getDuration());
            values.put(COLUMN_FLIGHT_PRICE, flight.getPrice());

            db.insert(TABLE_FLIGHTS, null, values);
        }
        db.close();
    }
    public boolean isFlightsTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT count(*) FROM " + TABLE_FLIGHTS;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ORDER_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_ORDERS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            Order order = new Order();
            order.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID)));
            order.setDepartureCity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DEPARTURE_CITY)));
            order.setDestinationCity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DESTINATION_CITY)));
            order.setDepartureTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DEPARTURE_TIME)));
            order.setArrivalTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ARRIVAL_TIME)));
            order.setTotalDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TOTAL_DURATION)));
            order.setFlightDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_FLIGHT_DATE)));
            orders.add(order);
        }
        cursor.close();
        db.close();
        return orders;
    }

}
