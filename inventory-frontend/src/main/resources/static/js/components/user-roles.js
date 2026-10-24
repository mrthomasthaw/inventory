const UserRoleView = {
    template: `
    <div class="max-w-6xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">User Role Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <button @click="fetchRoles" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                🔄 Refresh
            </button>
        </header>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

            <!-- Form Panel (Create / Edit) -->
            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-200 h-fit">
                <h2 class="text-lg font-semibold text-gray-900 mb-4">
                    {{ isEditing ? 'Edit User Role' : 'Create User Role' }}
                </h2>

                <form @submit.prevent="submitForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Role Name</label>
                        <input type="text" v-model="form.name" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition"
                               placeholder="e.g. ROLE_ADMIN">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Role Code</label>
                        <input type="text" v-model="form.code" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition"
                               placeholder="e.g. 101 or ADMIN_CODE">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Status</label>
                        <select v-model.number="form.status" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition bg-white">
                            <option :value="1">Active</option>
                            <option :value="2">Inactive</option>
                        </select>
                    </div>

                    <div class="flex items-center space-x-3 pt-2">
                        <button type="submit" 
                                class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-lg shadow-sm transition duration-150">
                            {{ isEditing ? 'Save Changes' : 'Create Role' }}
                        </button>

                        <button type="button" v-if="isEditing" @click="resetForm"
                                class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 px-4 rounded-lg transition duration-150">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

            <!-- User Roles Table Panel -->
            <div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Role List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ roles.length }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">Role Name</th>
                                    <th class="p-3">Code</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="roles.length === 0">
                                    <td colspan="6" class="text-center py-12 text-gray-400">
                                        No user roles found. Create one using the form on the left!
                                    </td>
                                </tr>
                                <tr v-for="(role, index) in roles" :key="role.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ index + 1 }}</td>
                                    <td class="p-3 font-semibold text-gray-900">
                                        {{ role.name }}
                                    </td>
                                    <td class="p-3 text-gray-600 font-mono text-xs">{{ role.code }}</td>
                                    <td class="p-3">
                                        <!-- Read status description and code from the status object -->
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': role.status && role.status.code === 1,
                                                  'bg-red-100 text-red-800': role.status && role.status.code === 2
                                              }">
                                            {{ role.status ? role.status.desc : 'Unknown' }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(role.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editRole(role)" 
                                                class="text-xs bg-gray-100 hover:bg-indigo-50 text-indigo-600 font-medium px-3 py-1.5 rounded-md transition border border-gray-200">
                                            ✏️ Edit
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
    `,
    data() {
        return {
            roles: [],
            isEditing: false,
            form: {
                id: null,
                name: '',
                code: '',
                status: 1 // Default to active code 1
            },
            backendUrl: 'http://localhost:8081/api/v1/user-roles'
        };
    },
    mounted() {
        this.fetchRoles();
    },
    methods: {
        async fetchRoles() {
            try {
                const response = await fetch(this.backendUrl);
                const result = await response.json();

                if (response.ok || result.code === 91) {
                    this.roles = result.data || [];
                }
            } catch (error) {
                console.error('Failed to fetch user roles:', error);
            }
        },

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        async submitForm() {
            if (this.isEditing) {
                await this.updateRole();
            } else {
                await this.createRole();
            }
        },

        async createRole() {
            try {
                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(this.form)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('User role created successfully!');
                    this.fetchRoles();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create user role'));
                }
            } catch (error) {
                console.error('Failed to create user role:', error);
            }
        },

        async updateRole() {
            try {
                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(this.form)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('User role updated successfully!');
                    this.fetchRoles();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update user role'));
                }
            } catch (error) {
                console.error('Failed to update user role:', error);
            }
        },

        editRole(role) {
            this.isEditing = true;
            this.form = {
                id: role.id,
                name: role.name,
                code: role.code,
                // Extract the numeric code from the status object for the dropdown binding
                status: role.status ? role.status.code : 1
            };
        },

        resetForm() {
            this.form = { id: null, name: '', code: '', status: 1 };
            this.isEditing = false;
        }
    }
};