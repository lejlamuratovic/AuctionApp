import axios from "axios";

import { BASE_URL } from "src/constants";

const API = axios.create({ baseURL: BASE_URL });

const getRequest = (endpoint) => API.get(endpoint).then((res) => res.data);

const postRequest = (endpoint, body) =>
  API.post(endpoint, body).then((res) => res.data);

const putRequest = (endpoint, body) =>
  API.put(endpoint, body).then((res) => res.data);

const deleteRequest = (endpoint) =>
  API.delete(endpoint).then((res) => res.data);

export { getRequest, postRequest, putRequest, deleteRequest };
