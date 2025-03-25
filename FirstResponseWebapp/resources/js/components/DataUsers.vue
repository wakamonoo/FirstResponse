<template>
    <v-card class="pa-4">
        <div class="d-flex align-center">
            <v-row>
                <v-col cols="12">
                    <div class="d-flex justify-space-between align-center">
                        <h2 class="text-h4">List of Users</h2>

                    </div>
                </v-col>
            </v-row>
        </div>

        <!-- Data Table to show the paginated items -->
        <v-table id="appointment-list" class="mt-4">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>User Name</th>
                    <th>Birthday</th>
                    <th>Email</th>
                    <th>Address</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in paginatedItems" :key="item.id">
                    <td>{{ item.id }}</td>
                    <td>{{ item.full_name }}</td>
                    <td>{{ item.username }}</td>
                    <td>{{ item.birthday }}</td>
                    <td>{{ item.email }}</td>
                    <td>{{ item.address  }}</td>
                </tr>
            </tbody>
        </v-table>

        <div class="ma-5">
            <v-row>
                <v-col cols="11">
                    <v-pagination
                        v-model="currentPage"
                        :length="totalPages"
                        @input="handlePageChange"
                        rounded="circle"
                    ></v-pagination>
                </v-col>
            </v-row>
        </div>
    </v-card>

    <!-- Create User Modal -->

</template>

<script>
export default {
    data() {
        return {
            tableData: [], // Holds user data
            itemsPerPage: 10,
            currentPage: 1,
        };
    },
    computed: {
        paginatedItems() {
            if (!this.tableData || this.tableData.length === 0) {
                return [];
            }
            const start = (this.currentPage - 1) * this.itemsPerPage;
            return this.tableData.slice(start, start + this.itemsPerPage);
        },
        totalPages() {
            return Math.ceil(this.tableData.length / this.itemsPerPage);
        },
    },
    async created() {
        await this.getdata();
    },
    methods: {
        handlePageChange(page) {
            this.currentPage = page;
        },
        async getdata() {
            try {
                const response = await fetch(
                    "https://firstresponseapp-b0184-default-rtdb.asia-southeast1.firebasedatabase.app/users.json"
                );
                const data = await response.json();


                this.tableData = Object.keys(data).map((key) => ({
                    id: key,
                    full_name: data[key].fullname || "",
                    username: data[key].username || "",
                    birthday: data[key].birthday || "",
                    email: data[key].email || "",
                    address: data[key].address || "",
                }));
            } catch (error) {
                console.error("Error fetching user data:", error);
            }
        },
    },
};
</script>


<style scoped>
.v-icon {
    cursor: pointer;
}
::v-deep .v-data-table__wrapper {
    overflow-y: hidden;
}
</style>
