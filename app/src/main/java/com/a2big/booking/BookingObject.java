package com.a2big.booking;

/*
import com.a2big.android.library.adapters.Setting.SettingManager;
import com.a2big.android.library.db.CouchbaseLiteManager;
import com.a2big.android.library.db.DBConstants;
import com.a2big.android.library.init.A2BigApp;
import com.a2big.android.library.objecttype.AbstractObject;
import com.a2big.android.library.utils.DevLog;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by a2big on 16. 3. 11..
 */
public class BookingObject extends AbstractObject {
   // private SettingManager mSettingManager;
   // private CouchbaseLiteManager mCBLManager;
   // private Database mDatabase;

    public BookingObject() {
        /*
        A2BigApp app = A2BigApp.getApplication();
        mSettingManager = app.getSettingManager();
        mCBLManager = app.getCouchbaseLiteManager();
        mDatabase = mCBLManager.getDatabase();
        */
    }

    private void createDocumentView(final String pDocId, final String pKey, String pValue) {
        /*
         String docId = getDocumentId(pDocId);

        if(docId != null) {
            // update the document
            Document retrieveDocument = mDatabase.getDocument(docId);
            Map<String, Object> updateProperties = new HashMap<String, Object>();
            updateProperties.putAll(retrieveDocument.getProperties());
            updateProperties.put(pKey, pValue);

            try {
                retrieveDocument.putProperties(updateProperties);
                DevLog.defaultLogging("updated retrievedDocument=" + String.valueOf(retrieveDocument.getProperties()));
            } catch (CouchbaseLiteException e) {
                DevLog.defaultLogging("Cannot update document " + e);
            }

            setView(pKey);
        } else {
            // create an empty document
            Document document = mDatabase.createDocument();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(pKey, pValue);

            // add content to document and write the document to the database
            try {
                document.putProperties(map);
                DevLog.defaultLogging("Document written to database named " +
                        DBConstants.TABLE_NAME_RESERVATION + " with ID = " + document.getId());
            } catch (CouchbaseLiteException e) {
                DevLog.defaultLogging("Cannot write document to database" + e);
            }

            setView(pKey);

            try {
                setDocumentId(pDocId, document.getId());
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
                DevLog.defaultLogging("CouchbaseLiteException: " + e);
            }
        }
        */
    }

    private void setView(final String pKey) {
        /*
        com.couchbase.lite.View view = mDatabase.getView(pKey);
        view.setMap(new Mapper() {

            @Override
            public void map(Map<String, Object> document, Emitter emitter) {
                Object object = document.get(pKey);
                if(object != null) {
                    emitter.emit(object, document);
                }
            }

        }, "1.0");
        */
    }

    private String getDocumentId(String pKey) {
        /*
        if(pKey.equals(DBConstants.KEY_BOOKING_LIST)) {
            return mSettingManager.getBookingDocId();
        }else {
            return null;
        }
        */
        return null;

    }

    /*
    private void setDocumentId(String pKey, String pDocumentId) throws CouchbaseLiteException {
        if(pKey.equals(DBConstants.KEY_BOOKING_LIST)) {
            mSettingManager.setBookingDocId(pDocumentId);
        }
    }
    */

    public BookingItems getBookingItem(String key){
      /*
        String docId = getDocumentId(DBConstants.KEY_BOOKING_LIST);
        Document retrieveDocument = mDatabase.getDocument(docId);
        BookingItems mItems1 = Json.jsonParsor(String.valueOf(retrieveDocument.getProperty(key)));
        return mItems1;
       */
      return null;
    }

    @Override
    public boolean onResponseListener(String pResponse) {

        Json t1 = new Json("2016-03-03","101","4","3","20000");
        Json t2 = new Json("2016-03-05","102","4","3","20000");
        Json t3 = new Json("2016-03-08","103","2","2","20000");
        Json t4 = new Json("2016-03-11","104","2","1","20000");
        Json t5 = new Json("2016-03-18","105","4","4","20000");
        Json t6 = new Json("2016-03-26","105","4","4","20000");
        Json t7 = new Json("2016-03-27","105","4","4","20000");
        Json t8 = new Json("2016-03-30","105","4","4","20000");

/*
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-03", t1.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-05", t2.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-08", t3.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-11", t4.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-18", t5.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-26", t6.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-27", t7.jsonCombine());
        createDocumentView(DBConstants.KEY_BOOKING_LIST,"2016-03-30", t8.jsonCombine());

        String docId = getDocumentId(DBConstants.KEY_BOOKING_LIST);
        Document retrieveDocument = mDatabase.getDocument(docId);


        BookingItems mItems1 = Json.jsonParsor(String.valueOf(retrieveDocument.getProperty("2016-03-08")));
        BookingItems mItems2 = Json.jsonParsor(String.valueOf(retrieveDocument.getProperty("2016-03-03") ));
        BookingItems mItems3 = Json.jsonParsor(String.valueOf(retrieveDocument.getProperty("2016-03-11") ));

      //  DevLog.defaultLogging("item1 " + mItems1.day + " "+mItems1.roomNo );
      //  DevLog.defaultLogging("item2 " + mItems2.day + " "+mItems2.roomNo );
      //  DevLog.defaultLogging("item3 " + mItems3.day + " "+mItems3.roomNo );
*/

        return true;
    }


    public static class Json {
        private String mValue1;
        private String mValue2;
        private String mValue3;
        private String mValue4;
        private String mValue5;


       /// String json_ex = "[{\"key01\":\"aaaa\",\"key02\":\"bbbb\"}]";

        public Json(String a1, String a2, String a3, String a4, String a5) {
            mValue1 = a1;
            mValue2 = a2;
            mValue3 = a3;
            mValue4 = a4;
            mValue5 = a5;

        }

        String jsonCombine() {
            JSONArray array = new JSONArray();
            JSONObject obj = new JSONObject();
            try {
                obj.put("day",          mValue1);
                obj.put("room_no",      mValue2);
                obj.put("total_number", mValue3);
                obj.put("number",       mValue4);
                obj.put("price",        mValue5);
                array.put(obj);
               // DevLog.defaultLogging("json parser ==================" + array.toString());

            } catch (Exception e) {
               // DevLog.defaultLogging("JSON Combine :::::array Error " + e.toString());
            }

            return array.toString();
        }

        static BookingItems jsonParsor(String parm) {
            String a = null;
            String b = null;
            BookingItems v = null;
            JSONArray ja = null;
            try {
                ja = new JSONArray(parm);
                JSONObject jsonRoot = ja.getJSONObject(0); //0번째 라인...다중 배열시엔 for문

                String day = jsonRoot.getString("day");
                String room_no = jsonRoot.getString("room_no");
                String total = jsonRoot.getString("total_number");
                String number = jsonRoot.getString("number");
                String price  = jsonRoot.getString("price");
                v = new BookingItems(day,room_no,total, number,price );

                } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }
    }
}
