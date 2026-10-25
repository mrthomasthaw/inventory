const UnitView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Unit Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <button @click="fetchUnits" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                🔄 Refresh
            </button>
        </header>

        <!-- Search & Filter Card -->
        <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-200 mb-6">
            <form @submit.prevent="handleSearch" class="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Code</label>
                    <input type="text" v-model="searchQuery.code" placeholder="Search code..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Name</label>
                    <input type="text" v-model="searchQuery.name" placeholder="Search name..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
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

            <!-- Form Panel (Create / Edit Unit) -->
            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-200 h-fit">
                <h2 class="text-lg font-semibold text-gray-900 mb-4">
                    {{ isEditing ? 'Edit Unit' : 'Create Unit' }}
                </h2>

                <form @submit.prevent="submitForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Code *</label>
                        <input type="text" v-model="form.code" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. PCS">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Name *</label>
                        <input type="text" v-model="form.name" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. Pieces">
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
                            {{ isEditing ? 'Save Changes' : 'Create Unit' }}
                        </button>
                        <button type="button" v-if="isEditing" @click="resetForm"
                                class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 px-4 rounded-lg transition">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

            <!-- Unit Table Panel -->
            <div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Unit List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ filteredUnits.length }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">Code</th>
                                    <th class="p-3">Name</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="filteredUnits.length === 0">
                                    <td colspan="6" class="text-center py-12 text-gray-400">
                                        No units found.
                                    </td>
                                </tr>
                                <tr v-for="(unit, index) in filteredUnits" :key="unit.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ index + 1 }}</td>
                                    <td class="p-3 font-mono font-medium text-indigo-600">{{ unit.code }}</td>
                                    <td class="p-3 text-gray-900 font-medium">{{ unit.name }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': getStatusCode(unit.status) === 1,
                                                  'bg-red-100 text-red-800': getStatusCode(unit.status) === 2
                                              }">
                                            {{ getStatusLabel(unit.status) }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(unit.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editUnit(unit)" 
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
            units: [],
            isEditing: false,
            searchQuery: {
                code: '',
                name: '',
                status: ''
            },
            activeSearchFilter: {
                code: '',
                name: '',
                status: ''
            },
            form: {
                id: null,
                code: '',
                name: '',
                status: 1
            },
            backendUrl: 'http://localhost:8081/api/v1/units'
        };
    },
    computed: {
        filteredUnits() {
            return this.units.filter(unit => {
                const matchCode = !this.activeSearchFilter.code || (unit.code && unit.code.toLowerCase().includes(this.activeSearchFilter.code.toLowerCase()));
                const matchName = !this.activeSearchFilter.name || (unit.name && unit.name.toLowerCase().includes(this.activeSearchFilter.name.toLowerCase()));

                const statusCode = this.getStatusCode(unit.status);
                const matchStatus = this.activeSearchFilter.status === '' || statusCode === parseInt(this.activeSearchFilter.status);

                return matchCode && matchName && matchStatus;
            });
        }
    },
    mounted() {
        this.fetchUnits();
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

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        async fetchUnits() {
            try {
                const response = await fetch(this.backendUrl);
                const result = await response.json();
                if (response.ok || result.code === 91) {
                    this.units = result.data || [];
                }
            } catch (error) {
                console.error('Failed to fetch units:', error);
            }
        },

        handleSearch() {
            this.activeSearchFilter = { ...this.searchQuery };
        },

        resetSearch() {
            this.searchQuery = { code: '', name: '', status: '' };
            this.activeSearchFilter = { code: '', name: '', status: '' };
        },

        async submitForm() {
            if (this.isEditing) {
                await this.updateUnit();
            } else {
                await this.createUnit();
            }
        },

        async createUnit() {
            try {
                const payload = {
                    code: this.form.code,
                    name: this.form.name,
                    status: parseInt(this.form.status)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Unit created successfully!');
                    this.fetchUnits();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create unit'));
                }
            } catch (error) {
                console.error('Failed to create unit:', error);
            }
        },

        async updateUnit() {
            try {
                const payload = {
                    id: this.form.id,
                    code: this.form.code,
                    name: this.form.name,
                    status: parseInt(this.form.status)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Unit updated successfully!');
                    this.fetchUnits();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update unit'));
                }
            } catch (error) {
                console.error('Failed to update unit:', error);
            }
        },

        editUnit(unit) {
            this.isEditing = true;
            this.form = {
                id: unit.id,
                code: unit.code,
                name: unit.name,
                status: this.getStatusCode(unit.status) ?? 1
            };
        },

        resetForm() {
            this.form = { id: null, code: '', name: '', status: 1 };
            this.isEditing = false;
        }
    }
};