import axios  from "axios";

import { i18Instance } from "../../locales";
export function signUp(body){
    return axios.post('/api/v1/users', body,{
        headers: {
            "Accept-Language": i18Instance.language
        }
    }

    );
}