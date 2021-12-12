import axios from 'axios'
const instance = axios.create({
  // Local
  baseURL: "http://localhost:7080/formychild/api",
  // Server
  // baseURL: "http://j4b205.p.ssafy.io:7080/formychild/api",
  headers: {
    "Content-type": "application/json",
    'Access-Control-Allow-Origin': "*",
    withCredential: true
  }
});

export default instance;