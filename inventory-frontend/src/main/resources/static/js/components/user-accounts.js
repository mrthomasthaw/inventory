const UserAccountView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">User Account Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <button @click="searchUsers" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                🔄 Refresh
            </button>
        </header>

        <!-- Search & Filter Card -->
        <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-200 mb-6">
            <form @submit.prevent="handleSearch" class="grid grid-cols-1 md:grid-cols-5 gap-4 items-end">
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Name</label>
                    <input type="text" v-model="searchQuery.name" placeholder="Search name..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Profile Name</label>
                    <input type="text" v-model="searchQuery.profileName" placeholder="Search username..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">User Role</label>
                    <select v-model="searchQuery.userRoleId"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                        <option value="">All Roles</option>
                        <option value="1">Admin</option>
                        <option value="2">Manager</option>
                        <option value="3">Sale</option>
                    </select>
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Status</label>
                    <select v-model="searchQuery.status"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                        <option value="">All Statuses</option>
                        <option value="1">Active</option>
                        <option value="2">Inactive</option>
                    </select>
                </div>
                <div class="flex space-x-2">
                    <button type="submit" class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-lg text-sm transition shadow-sm">
                        Search
                    </button>
                    <button type="button" @click="resetSearch" class="bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium py-2 px-3 rounded-lg text-sm transition">
                        Reset
                    </button>
                </div>
            </form>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

            <!-- Form Panel (Create / Edit User Account) -->
            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-200 h-fit">
                <h2 class="text-lg font-semibold text-gray-900 mb-4">
                    {{ isEditing ? 'Edit User Account' : 'Create User Account' }}
                </h2>

                <form @submit.prevent="submitForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Full Name *</label>
                        <input type="text" v-model="form.name" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. John Doe">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Profile Name / Username *</label>
                        <input type="text" v-model="form.profileName" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. johndoe">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">
                            Password <span v-if="isEditing" class="text-xs text-gray-400">(leave blank to keep current)</span>
                        </label>
                        <input type="password" v-model="form.password" :required="!isEditing"
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="••••••••">
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">User Role *</label>
                        <select v-model.number="form.userRoleId" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option :value="1">Admin</option>
                            <option :value="2">Manager</option>
                            <option :value="3">Sale</option>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Status *</label>
                        <select v-model.number="form.status" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option :value="1">Active</option>
                            <option :value="2">Inactive</option>
                        </select>
                    </div>

                    <div class="flex items-center space-x-3 pt-2">
                        <button type="submit" 
                                class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-lg shadow-sm transition">
                            {{ isEditing ? 'Save Changes' : 'Create Account' }}
                        </button>
                        <button type="button" v-if="isEditing" @click="resetForm"
                                class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 px-4 rounded-lg transition">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

            <!-- User Accounts Table Panel with Pagination -->
            <div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">User List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ totalCount }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">Name</th>
                                    <th class="p-3">Profile Name</th>
                                    <th class="p-3">User Role</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="users.length === 0">
                                    <td colspan="7" class="text-center py-12 text-gray-400">
                                        No user accounts found.
                                    </td>
                                </tr>
                                <tr v-for="(user, index) in users" :key="user.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ (currentPage * pageSize) + index + 1 }}</td>
                                    <td class="p-3 font-medium text-gray-900">{{ user.name }}</td>
                                    <td class="p-3 text-gray-600">@{{ user.profileName }}</td>
                                    <td class="p-3 text-gray-600">{{ getRoleLabel(user.userRole) }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': getStatusCode(user.status) === 1,
                                                  'bg-red-100 text-red-800': getStatusCode(user.status) === 2
                                              }">
                                            {{ getStatusLabel(user.status) }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(user.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editUser(user)" 
                                                class="text-xs bg-gray-100 hover:bg-indigo-50 text-indigo-600 font-medium px-3 py-1.5 rounded-md transition border border-gray-200">
                                            ✏️ Edit
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Pagination Bar -->
                <div class="bg-gray-50 px-6 py-3 border-t border-gray-200 flex items-center justify-between text-sm text-gray-600">
                    <div>
                        Showing <span class="font-medium">{{ startItemIndex }}</span> to <span class="font-medium">{{ endItemIndex }}</span> of <span class="font-medium">{{ totalCount }}</span>
                    </div>
                    <div class="flex items-center space-x-2">
                        <button @click="changePage(-1)" :disabled="currentPage === 0"
                                class="px-3 py-1.5 border border-gray-300 rounded-lg bg-white text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                            Previous
                        </button>
                        <span>Page {{ currentPage + 1 }} of {{ totalPages || 1 }}</span>
                        <button @click="changePage(1)" :disabled="(currentPage + 1) >= totalPages"
                                class="px-3 py-1.5 border border-gray-300 rounded-lg bg-white text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">
                            Next
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </div>
    `,
    data() {
        return {
            users: [],
            totalCount: 0,
            currentPage: 0,
            pageSize: 10,
            totalPages: 1,
            isEditing: false,
            searchQuery: {
                name: '',
                profileName: '',
                userRoleId: '',
                status: ''
            },
            form: {
                id: null,
                name: '',
                profileName: '',
                password: '',
                status: 1,
                userRoleId: 1
            },
            backendUrl: 'http://localhost:8081/api/v1/user-accounts'
        };
    },
    computed: {
        startItemIndex() {
            return this.totalCount === 0 ? 0 : (this.currentPage * this.pageSize) + 1;
        },
        endItemIndex() {
            return Math.min((this.currentPage + 1) * this.pageSize, this.totalCount);
        }
    },
    mounted() {
        this.searchUsers();
    },
    methods: {
        getStatusCode(status) {
            if (status === null || status === undefined) return null;
            return typeof status === 'object' ? status.code : status;
        },

        getStatusLabel(status) {
            if (status === null || status === undefined) return 'Unknown';
            if (typeof status === 'object' && status.desc) return status.desc;
            const code = this.getStatusCode(status);
            if (code === 1) return 'Active';
            if (code === 2) return 'Inactive';
            return 'Unknown';
        },

        getRoleId(role) {
            if (role === null || role === undefined) return null;
            if (typeof role === 'object') return role.id || role.code;
            if (typeof role === 'string') {
                if (role.toLowerCase() === 'admin') return 1;
                if (role.toLowerCase() === 'manager') return 2;
                if (role.toLowerCase() === 'sale') return 3;
            }
            return parseInt(role);
        },

        getRoleLabel(role) {
            if (role === null || role === undefined) return '-';
            if (typeof role === 'object') return role.name || role.desc;
            const id = this.getRoleId(role);
            if (id === 1) return 'Admin';
            if (id === 2) return 'Manager';
            if (id === 3) return 'Sale';
            return role;
        },

        async searchUsers() {
            try {
                const payload = {
                    name: this.searchQuery.name || null,
                    profileName: this.searchQuery.profileName || null,
                    userRoleId: this.searchQuery.userRoleId !== '' ? parseInt(this.searchQuery.userRoleId) : null,
                    status: this.searchQuery.status !== '' ? parseInt(this.searchQuery.status) : null,
                    page: this.currentPage,
                    pageSize: this.pageSize
                };

                const response = await fetch(`${this.backendUrl}/search`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });

                const result = await response.json();
                if (response.ok) {
                    const pageResult = result.data;
                    this.users = pageResult.list || [];
                    this.totalCount = pageResult.totalCount || 0;
                    this.totalPages = Math.ceil(this.totalCount / this.pageSize) || 1;
                }
            } catch (error) {
                console.error('Failed to search user accounts:', error);
            }
        },

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        handleSearch() {
            this.currentPage = 0; // Reset to first page on new search
            this.searchUsers();
        },

        resetSearch() {
            this.searchQuery = { name: '', profileName: '', userRoleId: '', status: '' };
            this.currentPage = 0;
            this.searchUsers();
        },

        changePage(direction) {
            this.currentPage += direction;
            this.searchUsers();
        },

        async submitForm() {
            if (this.isEditing) {
                await this.updateUser();
            } else {
                await this.createUser();
            }
        },

        async createUser() {
            try {
                const payload = {
                    ...this.form,
                    status: parseInt(this.form.status),
                    userRoleId: parseInt(this.form.userRoleId)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('User created successfully!');
                    this.searchUsers();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create user'));
                }
            } catch (error) {
                console.error('Failed to create user:', error);
            }
        },

        async updateUser() {
            try {
                const payload = {
                    ...this.form,
                    status: parseInt(this.form.status),
                    userRoleId: parseInt(this.form.userRoleId)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('User updated successfully!');
                    this.searchUsers();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update user'));
                }
            } catch (error) {
                console.error('Failed to update user:', error);
            }
        },

        editUser(user) {
            this.isEditing = true;
            this.form = {
                id: user.id,
                name: user.name,
                profileName: user.profileName,
                password: '',
                status: this.getStatusCode(user.status) ?? 1,
                userRoleId: this.getRoleId(user.userRole) ?? 1
            };
        },

        resetForm() {
            this.form = { id: null, name: '', profileName: '', password: '', status: 1, userRoleId: 1 };
            this.isEditing = false;
        }
    }
};