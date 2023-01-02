import axios from "axios";

const instance = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}`,
  headers: {
    authorization: `Bearer ${window.sessionStorage.getItem("accessToken")}`,
    refreshToken: `Bearer ${window.sessionStorage.getItem("refreshToken")}`,
  },
  withCredentials: true
});

export default instance;
