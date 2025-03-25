import { createRouter, createWebHistory } from "vue-router";
import { Profile } from "../stores/profilestate";
import PageLayout from "@/layouts/PageLayout.vue";
import Dashboard from "../pages/Dashboard.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/login",
            name: "login",
            component: () => import("@/pages/Login.vue"),
            meta: { title: "Login - Pilar Sorsogon Website", requiresAuth: false } // Public route
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: Dashboard
            },
            meta: { title: "Dashboard - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/data-complaint',
            name: 'data-complaint',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataView.vue')
            },
            meta: { title: "Data Complaint View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/complaint-archive',
            name: 'archive-complaint',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/ViewComplaintArchive.vue')
            },
            meta: { title: "Archive Data Complaint View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/cms-homebanner',
            name: 'cms-homebanner',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataHomeBannerView.vue')
            },
            meta: { title: "Data Complaint View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/cms-tourism',
            name: 'cms-tourism',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewTourism.vue')
            },
            meta: { title: "Data Tourism View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/archive-banner',
            name: 'archive-banner',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/ViewArchiveBanner.vue')
            },
            meta: { title: "Data Complaint View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/data-appointment',
            name: 'data-appointment',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewAppointment.vue')
            },
            meta: { title: "Data Appointment View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/data-news',
            name: 'data-news',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewNews.vue')
            },
            meta: { title: "Data News View - Pilar Sorsogon Website", requiresAuth: true }
        },{
            path: '/data-agencies',
            name: 'data-agencies',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewLineAgencies.vue')
            },
            meta: { title: "Data Agencies View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/data-municipalofficers',
            name: 'data-municipalofficers',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataViewMunicipalOfficer.vue')
            },
            meta: { title: "Data Municipal Officers View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: '/users',
            name: 'users',
            components: {
                'left-nav': () => import('../components/LeftNavigation.vue'),
                'top-nav': () => import('../components/TopNavigation.vue'),
                default: () => import('@/pages/DataUsersView.vue')
            },
            meta: { title: "Users View - Pilar Sorsogon Website", requiresAuth: true }
        },
        {
            path: "/",
            component: PageLayout,
            children: [
                {
                    path: "",
                    component: () => import("@/pages/Home.vue"),
                    meta: { title: "Home - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/about-us",
                    component: () => import("@/pages/AboutUs.vue"),
                    meta: { title: "About Us - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/mayors-corner",
                    component: () => import("@/pages/MayorsCorner.vue"),
                    meta: { title: "About Us - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/appointment",
                    name: "appointment",
                    component: () => import("@/pages/Appointment.vue"),
                    meta: { title: "Appointment - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/file-complaint",
                    name: "complaint",
                    component: () => import("@/pages/Complaint.vue"),
                    meta: { title: "File a Complaint - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/news/:title",
                    component: () => import("@/pages/NewsDetails.vue"),
                    meta: { title: "News and Announcement - Pilar Sorsogon Website", requiresAuth: false }
                },

                {
                    path: "/news-announcement",
                    name: "News and Announcement",
                    component: () => import("@/pages/NewsandAnnouncement.vue"),
                    meta: { title: "News and Announcement - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/services",
                    name: "Services",
                    component: () => import("@/pages/Services.vue"),
                    meta: { title: "Services - Pilar Sorsogon Website", requiresAuth: false }
                },

                {
                    path: "/contact-us",
                    name: "Contact Us",
                    component: () => import("@/pages/ContactUsView.vue"),
                    meta: { title: "Contact Us - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/tourism",
                    name: "Tourism",
                    component: () => import("@/pages/Tourism.vue"),
                    meta: { title: "Tourism  - Pilar Sorsogon Website", requiresAuth: false }
                },
                {
                    path: "/our-officer",
                    name: "Municipal employee",
                    component: () => import("@/pages/OurOfficerView.vue"),
                    meta: { title: "Tourism  - Pilar Sorsogon Website", requiresAuth: false }
                },
            ],
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

    if (to.meta.requiresAuth && !profileStore.token) {
       next({ name: 'login' });
    } else {
       next();
    }
});

export default router;
