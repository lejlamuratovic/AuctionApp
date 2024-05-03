import { 
  seller, 
  profile, 
  settings, 
  bids, 
  sellerActive,
  profileActive,
  settingsActive,
  bidsActive  
} from "src/assets/icons";

export const BASE_URL = "http://localhost:8080/api/v1";

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
  ADD_ITEM: "ADD ITEM",
  CHANGE_PHOTO: "Change photo",
  SAVE_INFO: "SAVE INFO",
  BID: "BID",
  DEACTIVATE: "DEACTIVATE",
};

export const BUTTON_VARIANTS = {
  OUTLINED: "outlined",
  FILLED: "filled"
}

export const MY_ACCOUNT_TABS = [
  { id: "profile", label: "Profile", icon: profile, activeIcon: profileActive},
  { id: "seller", label: "Seller", icon: seller, activeIcon: sellerActive},
  { id: "bids", label: "Bids", icon: bids, activeIcon: bidsActive},
  { id: "settings", label: "Settings", icon: settings, activeIcon: settingsActive},
];

export const HEADERS = [
  { id: 'image', text: 'Item' },
  { id: 'name', text: 'Name' },
  { id: 'timeLeft', text: 'Time Left' },
  { id: 'yourPrice', text: 'Your Price' },
  { id: 'noBids', text: 'No. Bids' },
  { id: 'highestBid', text: 'Highest Bid' },
  { id: 'actions', text: '' }
];

export const SELLER_TABS = [
  { id: "active", label: "Active" },
  { id: "sold", label: "Sold" },
];

export const ITEMS = [
  {
    id: 1,
    imgSrc: "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/2eff461f-f3ac-4285-9c6a-2f22173aac42/custom-nike-air-force-1-low-by-you.png",
    title: "Product 1",
    timeLeft: "1 day 2 hours",
    noBids: 5,
    yourPrice: "100",
    highestBid: "120"
  }, 
  {
    id: 2,
    imgSrc: "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/2eff461f-f3ac-4285-9c6a-2f22173aac42/custom-nike-air-force-1-low-by-you.png",
    title: "Product 2",
    timeLeft: "2 days 4 hours",
    noBids: 3,
    yourPrice: "150",
    highestBid: "200"
  }
];

export const POLICY_AND_COMMUNITY = [
  { id: "email", label: "Email" },
  { id: "pushNotifications", label: "Push Notifications" },
  { id: "smsNotifications", label: "SMS" },
];
