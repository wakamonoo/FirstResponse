<template>
    <v-navigation-drawer
        :rail="rail"
        permanent
        style="position: fixed"
        id="left-drawer-parent"
    >
        <v-list-item
            :title="`${defaultUser.firstName} ${defaultUser.lastName}`"
            :subtitle="defaultUser.email"
            nav
        ></v-list-item>
        <v-divider></v-divider>

        <v-list density="compact" nav id="trigger-nav">
            <v-list-item
                prepend-icon="mdi mdi-bell-alert-outline"
                title="Emergency Notification"
                value="complaint"
                @click="$router.push('/data-emergency')"
            ></v-list-item>
            <v-list-item
                prepend-icon="mdi-account-group-outline"
                title="Users"
                value="users"
                @click="$router.push('/users')"
            ></v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
import { ref } from "vue";

export default {
    setup() {
        const rail = ref(false);

        const defaultUser = ref({
            firstName: "Default",
            lastName: "User",
            email: "user@example.com",
        });

        return {
            rail,
            defaultUser,
        };
    },
};
</script>

<style lang="scss" scoped>
.v-list-item--density-default {
    min-height: 65px;
}
</style>
