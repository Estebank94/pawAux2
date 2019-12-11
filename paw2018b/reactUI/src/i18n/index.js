/**
 * Created by estebankramer on 15/10/2019.
 */
import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

import en from './en';
import es from './es';

// not like to use this?
// have a look at the Quick start guide
// for passing in lng and translations on init

i18n
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .init({
        fallbackLng: 'es',
        debug: true,

        interpolation: {
            escapeValue: false, // not needed for react as it escapes by default
        },
        resources: {
          es: {translation: es},
          en: {translation: en},
        },
    });


export default i18n;