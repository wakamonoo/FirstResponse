<template>
    <v-app>
        <MainNavbar />
        <v-main>
            <router-view />
        </v-main>
        <MainFooter />
    </v-app>
</template>

<script>
import MainNavbar from "@/layouts/Default/MainHeader.vue";
import MainFooter from "@/layouts/Default/MainFooter.vue";
export default {
    name: "PageLayout",
    components: { MainNavbar, MainFooter },
    computed: {
    }
};
</script>
<style scoped></style>
