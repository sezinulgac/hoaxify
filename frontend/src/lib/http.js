import axios from 'axios';
import { i18Instance } from "@/locales";

const http = axios.create();

http.interceptors.request.use( (config) =>{
    config.headers['Accept-Language'] = i18Instance.language;
    return config;


})

export default http;

