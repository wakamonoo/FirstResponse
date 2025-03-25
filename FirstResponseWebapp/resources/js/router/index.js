import { createRouter, createWebHistory } from "vue-router";
import { Profile } from "../stores/profilestate";
import Dashboard from "../pages/Dashboard.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/login",
            name: "login",
            component: () => import("@/pages/Login.vue"),
            meta: { title: "Login - First Responder App", requiresAuth: false }, // Public route
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: Dashboard
            },
            meta: { title: "Dashboard - First Responder App", requiresAuth: false }, // Protected route
        },
        {
            path: '/data-emergency',
            name: 'data-emergency',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewListEmergency.vue')
            },
            meta: { title: "Dashboard - First Responder App", requiresAuth: false }, // Protected route
        },
        {
            path: '/users',
            name: 'users',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataUsersView.vue')
            },
            meta: { title: "Dashboard - First Responder App", requiresAuth: false }, // Protected route
        },
        {
            path: "/",
            redirect: "/login", // Redirect root to login
        },
        {
            path: "/:catchAll(.*)",
            redirect: "/login", // Redirect invalid routes to login
        },
    ],


    scrollBehavior(to, from, savedPosition) {
        if (to.path !== from.path) {
            return { left: 0, top: 0 };
        }
    },
});

router.beforeEach((to, from, next) => {
    const defaultTitle = 'Pilar Sorsogon Website';
    document.title = to.meta.title || defaultTitle;

    const profileStore = Profile();

    profileStore.loadStorageData();

    next();
});

export default router;
