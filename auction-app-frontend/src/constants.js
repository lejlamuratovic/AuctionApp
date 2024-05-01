export const BASE_URL = "http://localhost:8080/api/v1";

export const WEB_SOCKET_BASE_URL = "ws://localhost:8080/websocket";

export const NAV_ITEMS = [
  {
    label: "HOME",
    link: "/",
    key: "home",
  },
  {
    label: "SHOP",
    link: "/shop",
    key: "shop",
  },
  {
    label: "MY ACCOUNT",
    link: "/my-account",
    key: "my-account",
  },
];

export const ROUTES_MAP = {
  "/": "Home",
  "/shop": "Shop",
  "/my-account": "My Account",
  "/about-us": "About Us",
  "/privacy-policy": "Privacy Policy",
  "/terms-and-conditions": "Terms and Conditions",
  // will add more routes
};

export const ROUTE_PATHS = {
  HOME: "/",
  SHOP: "/shop",
  MY_ACCOUNT: "/my-account",
  ABOUT_US: "/about-us",
  PRIVACY_POLICY: "/privacy-policy",
  TERMS_AND_CONDITIONS: "/terms-and-conditions",
  SHOP: "/shop",
  PRODUCT: "/product",
  LOGIN: "/login",
  REGISTER: "/register",
};

export const HIDE_BREADCRUMBS_ON_PATHS = [
  ROUTE_PATHS.HOME,
  ROUTE_PATHS.SHOP,
  ROUTE_PATHS.LOGIN,
  ROUTE_PATHS.REGISTER,
];

export const HIDE_NAV_OPTIONS_ON_PATHS = [
  ROUTE_PATHS.LOGIN,
  ROUTE_PATHS.REGISTER,
];

export const HOME_TABS = [
  { id: "newArrivals", label: "New Arrivals" },
  { id: "lastChance", label: "Last Chance" },
];

export const PRODUCT_DETAILS_TABS = [{ id: "details", label: "Details" }];

export const SEARCH_RESULTS = "Search results for"

export const SHOP_DEFAULT_PAGE_NUMBER = 9;

export const HOME_DEFAULT_PAGE_NUMBER = 8;

export const BUTTON_LABELS = {
  LOGIN: "LOGIN",
  REGISTER: "REGISTER",
  PLACE_BID: "PLACE BID",
};

export const BUTTON_VARIANTS = {
  OUTLINED: "outlined",
  FILLED: "filled"
}

export const CONNECTION_STATUSES = {
  CONNECTED: "connected",
  DISCONNECTED: "Disconnected",
};

export const USER_TYPES = {
  ADMIN: "ADMIN",
  USER: "USER"
}

export const AUCTION_STATUS = {
  EXPIRED: "Expired",
};
