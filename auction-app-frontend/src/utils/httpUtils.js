import axios from "axios";

const API = axios.create({ baseURL: "http://localhost:8080/api/v1" });

const getRequest = (endpoint) => API.get(endpoint).then((res) => res.data);

const postRequest = (endpoint, body) =>
  API.post(endpoint, body).then((res) => res.data);

const putRequest = (endpoint, body) =>
  API.put(endpoint, body).then((res) => res.data);

const deleteRequest = (endpoint) =>
  API.delete(endpoint).then((res) => res.data);

export { getRequest, postRequest, putRequest, deleteRequest };
