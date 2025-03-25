import { createApp } from 'vue';
import App from './App.vue'; // Main app component
import router from './router'; // Vue Router
import { registerPlugins } from '@/plugins';
import '@mdi/font/css/materialdesignicons.css';
import 'vuetify/styles';
import { createVuetify } from 'vuetify';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import { createPinia } from 'pinia'
import 'aos/dist/aos.css'
import AOS from 'aos'
const vuetify = createVuetify({
  components,
  directives,
  theme: {
    themes: {
      light: {
        colors: {
            primary: '#5F91C8',
            secondary: '#1D508F',
            "deep-blue": '#10304B',
            "dark-blue": '#1E293B',
            "blue1": "#006EAD",
            red: "#FF0000",
            gray: "#EFF1F1",
            "gray-light-cs": "#E6E6E605"
        },
      },
    },
  },
  aliases: {
    VBtnPrimary: 'VBtn',
    VBtnSecondary: 'VBtn',
    VBtnTertiary: 'VBtn',
  },
  defaults: {
    VBtn: {
      class: "text-none",
    },
    VBtnPrimary: {
      rounded: "lg",
      color: "primary",
      variant: "flat",
      class: "text-none font-weight-medium",
      height: "50",
      minWidth: 160,
    },
    VBtnSecondary: {
      rounded: "lg",
      color: "secondary",
      variant: "flat",
      class: "text-none text-black font-weight-medium",
      height: "50",
    },
    VBtnTertiary: {
      rounded: "lg",
      color: "light-primary",
      variant: "flat",
      class: "text-none font-weight-medium",
      height: "50",
    },
  },
});

const pinia = createPinia()
const app = createApp(App);
app.use(AOS.init())
// Register Plugins
registerPlugins(app);

// Use Vue Router
app.use(router);

// Use Vuetify with the custom configuration
app.use(vuetify);
app.use(pinia);
// Mount the app
app.mount('#app');
