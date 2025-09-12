import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import en from "./translations/en.json";
import tr from "./translations/tr.json";
const initialLanguage = localStorage.getItem("language") || 
navigator.language

export const i18Instance =
i18n.use(initReactI18next) // passes i18n down to react-i18next
  i18Instance.init({
    resources: {
      en: {
        translation: en,
      },
      tr: {
        translation: tr,
      },
    },
    fallbackLng: initialLanguage,

    interpolation: {
      escapeValue: false,
    },
  });
