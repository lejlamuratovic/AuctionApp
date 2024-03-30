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
  PRODUCT_DETAILS: "/shop/:id",
};

export const HIDE_BREADCRUMBS_ON_PATHS = [
  ROUTE_PATHS.HOME,
  // will add more routes when needed
];

export const HOME_TABS = [
  { id: "newArrivals", label: "New Arrivals" },
  { id: "lastChance", label: "Last Chance" },
];

export const PRODUCT_DETAILS_TABS = [{ id: "details", label: "Details" }];

export const PRODUCT_DETAILS_IMAGES = [
  "https://d1flfk77wl2xk4.cloudfront.net/Assets/38/112/XXL_p0192611238.jpg",
  "https://m.media-amazon.com/images/I/61VBrvviDkL._AC_SR920,736_.jpg",
  "https://hips.hearstapps.com/hmg-prod/images/common-houseplants-to-grow-1641843117.jpeg?crop=0.668xw:1.00xh;0.104xw,0&resize=1200:*",
  "https://www.marthastewart.com/thmb/Gt3EKxaUS8T1WzoSVAmd4UGSFTo=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/low-light-houseplants-snake-plant-hero-getty-1123-bb1e9fd1b2024e879a45c3e6bbd9fcfe.jpg",
  "https://d1flfk77wl2xk4.cloudfront.net/Assets/38/112/XXL_p0192611238.jpg",
  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz4sxP2DKqASm8rQAalmYu5auMDKn-WsDLy_AWXylrwpvvAyf-nIYWT2wKIR6ldTxBlac&usqp=CAU",
];
