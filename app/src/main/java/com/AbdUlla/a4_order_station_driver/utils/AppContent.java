package com.AbdUlla.a4_order_station_driver.utils;

public interface AppContent {
    String APP_NAME = "4 OrderStation Station";
    String INPUT_SETTINGS = "setting";
    String CODE_DETAILS = "codeDetails";

    String BASE_URL = "http://safeersa.com/api/v3/";
    //seventh String BASE_URL = "https://www.4orderstation.net/api/v3/";
    //sixth String BASE_URL = "https://satco.vip/api/v3/";
    //fifth String BASE_URL = "http://www.4order.net/api/v3/";
    //forth String BASE_URL = "http://4orderstation.com/api/v2/";
    //third String BASE_URL = "https://live.4orderstation.net/api/v2/";
    //second url String BASE_URL = "https://stationsback.4orderstation.net/api/v2/";
    //first url String BASE_URL = https://control.4orderstation.net/api/v2/

    //first String IMAGE_STORAGE_URL = "https://satco.vip/";
    String IMAGE_STORAGE_URL = "http://safeersa.com/";
    //navigation
    String PAGE = "page";
    String ORDER_Id = "order_id";

    String ORDER_OBJECT = "order_object";

    //request from camera or gallery for register
    int REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA = 1000;
    int REQUEST_IMAGE_VEHICLE_UPLOAD = 1001;
    int REQUEST_IMAGE_VEHICLE_CAMERA = 1002;
    int REQUEST_IMAGE_VEHICLE_LICENSE_UPLOAD = 1003;
    int REQUEST_IMAGE_VEHICLE_LICENSE_CAMERA = 1004;
    int REQUEST_IMAGE_VEHICLE_INSURANCE_UPLOAD = 1005;
    int REQUEST_IMAGE_VEHICLE_INSURANCE_CAMERA = 1006;
    int REQUEST_IMAGE_IDENTITY_UPLOAD = 1007;
    int REQUEST_IMAGE_IDENTITY_CAMERA = 1008;
    int REQUEST_IMAGE_AVATAR_CAMERA = 1009;
    int REQUEST_IMAGE_AVATAR_UPLOAD = 1010;
    int PHONE_CALL_CODE = 1011;
    int REQUEST_CAMERA = 1012;
    int REQUEST_STUDIO = 1013;
    int REQUEST_IMAGE_YOUR_LICENSE_UPLOAD = 1014;
    int REQUEST_IMAGE_YOUR_LICENSE_CAMERA = 1015;

    //public order status
    String CUSTOMER_WAITING = "customer_waiting";
    String TO_STORE_STATUS = "in_the_way_to_store";
    String TO_CLIENT_STATUS = "in_the_way_to_client";
    String ON_DELIVERY_STATUS = "on_delivery";
    String DELIVERED_STATUS = "delivered";
    String CANCELLED_STATUS = "cancelled";
    String CANCEL_STATUS = "cancel";
    String READY_STATUS = "ready";
    String PENDING_STATUS = "pending";
    String CONFIRM_DELIVIERY = "confirm_deliverd";

    //map to reset password
    String RESET_MOBILE = "mobile";
    String RESET_CODE = "code";
    String RESET_TOKEN = "token";
    String FILE_PROVIDER_AUTHORITY = "com.AbdUlla.a4_order_station_driver.provider";
    String FILE_PROVIDER_PATH = "/Android/data/com.AbdUlla.a4_order_station_driver/files/Pictures";
    //API
    //order type
    String TYPE_ORDER_PUBLIC = "public";
    String TYPE_ORDER_4STATION = "4orderstation";
    String ADMIN = "admin";
    String WALLET = "wallet";

    //FIREBASE
    String FIREBASE_MESSAGE = "message";
    String FIREBASE_DATA = "data";
    String FIREBASE_TYPE = "type";
    String FIREBASE_MSG = "msg";
    String FIREBASE_STATUS = "status";
    String FIREBASE_CHAT_INSTANCE = "Chat";
    String FIREBASE_PUBLIC_STORE_CHAT_INSTANCE = "PublicStoreChat";
    String FIREBASE_PUBLIC_TRACKING_INSTANCE = "delivery_app_tracking";
    String FIREBASE_PUBLIC_INSTANCE = "delivery_app";
    //FIREBASE DATA
    String DRIVER_APPROVED = "driver_approved";
    String PAYMENT_CONFIRM = "payment_confirm";
    String REJECT = "reject";
    String NEW_MESSAGE = "new_message";
    String IN_WAY_TO_STORE = "in_the_way_to_store";
    String NEW_ORDER = "neworder";
    String RATE = "rate";

    //TRACKING
    String PUBLIC_TRACKING_INSTANCE = "PublicTracking";
    String TRACKING_INSTANCE = "Tracking";

    String ONE = "1";
    String SUBSCRIBE_STATUS = "withdraw";
}

