const SupplierView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Supplier Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <button @click="searchSuppliers" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                🔄 Refresh
            </button>
        </header>

        <!-- Search & Filter Card -->
        <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-200 mb-6">
            <form @submit.prevent="handleSearch" class="grid grid-cols-1 md:grid-cols-5 gap-4 items-end">
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
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Address</label>
                    <input type="text" v-model="searchQuery.address" placeholder="Search address..."
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

            <!-- Form Panel (Create / Edit Supplier) -->
            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-200 h-fit">
                <h2 class="text-lg font-semibold text-gray-900 mb-4">
                    {{ isEditing ? 'Edit Supplier' : 'Create Supplier' }}
                </h2>

                <form @submit.prevent="submitForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Code *</label>
                        <input type="text" v-model="form.code" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. SUP-001">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Name *</label>
                        <input type="text" v-model="form.name" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. ABC Supply Co.">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Address *</label>
                        <textarea v-model="form.address" rows="2" required
                                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                                  placeholder="Enter address..."></textarea>
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
                            {{ isEditing ? 'Save Changes' : 'Create Supplier' }}
                        </button>
                        <button type="button" v-if="isEditing" @click="resetForm"
                                class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 px-4 rounded-lg transition">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

            <!-- Supplier Table Panel with Pagination -->
            <div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Supplier List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ totalCount }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">Id</th>
                                    <th class="p-3 pl-4">Code</th>
                                    <th class="p-3">Name</th>
                                    <th class="p-3">Address</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="suppliers.length === 0">
                                    <td colspan="5" class="text-center py-12 text-gray-400">
                                        No suppliers found.
                                    </td>
                                </tr>
                               <tr v-for="(supplier, index) in suppliers" :key="supplier.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ (currentPage * pageSize) + index + 1 }}</td>
                                    <td class="p-3 pl-4 font-mono font-medium text-indigo-600">{{ supplier.code }}</td>
                                    <td class="p-3 text-gray-900 font-medium">{{ supplier.name }}</td>
                                    <td class="p-3 text-gray-500 truncate max-w-xs">{{ supplier.address || '-' }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': supplier.status && supplier.status.code === 1,
                                                  'bg-red-100 text-red-800': supplier.status && supplier.status.code === 2,
                                                  'bg-yellow-100 text-yellow-800': supplier.status && supplier.status.code === 3
                                              }">
                                            {{ supplier.status ? supplier.status.desc : 'Unknown' }}
                                        </span>
                                    </td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editSupplier(supplier)" 
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
            suppliers: [],
            totalCount: 0,
            currentPage: 0,
            pageSize: 10,
            totalPages: 1,
            isEditing: false,
            searchQuery: {
                code: '',
                name: '',
                address: '',
                status: ''
            },
            form: {
                id: null,
                code: '',
                name: '',
                address: '',
                status: 1,
                customer: false
            },
            backendUrl: 'http://localhost:8081/api/v1/business-partners'
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
        this.searchSuppliers();
    },
    methods: {
        async searchSuppliers() {
            try {
                const payload = {
                    customer: false, // Forces supplier-specific filtering for search requests using 'customer' field
                    code: this.searchQuery.code || null,
                    name: this.searchQuery.name || null,
                    address: this.searchQuery.address || null,
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
                    this.suppliers = pageResult.list || [];
                    this.totalCount = pageResult.totalCount || 0;
                    this.totalPages = Math.ceil(this.totalCount / this.pageSize) || 1;
                }
            } catch (error) {
                console.error('Failed to search suppliers:', error);
            }
        },

        handleSearch() {
            this.currentPage = 0;
            this.searchSuppliers();
        },

        resetSearch() {
            this.searchQuery = { code: '', name: '', address: '', status: '' };
            this.currentPage = 0;
            this.searchSuppliers();
        },

        changePage(direction) {
            this.currentPage += direction;
            this.searchSuppliers();
        },

        async submitForm() {
            if (this.isEditing) {
                await this.updateSupplier();
            } else {
                await this.createSupplier();
            }
        },

        async createSupplier() {
            try {
                const payload = {
                    code: this.form.code,
                    name: this.form.name,
                    address: this.form.address,
                    isCustomer: false,
                    status: parseInt(this.form.status)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Supplier created successfully!');
                    this.searchSuppliers();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create supplier'));
                }
            } catch (error) {
                console.error('Failed to create supplier:', error);
            }
        },

        async updateSupplier() {
            try {
                const payload = {
                    id: this.form.id,
                    code: this.form.code,
                    name: this.form.name,
                    address: this.form.address,
                    status: parseInt(this.form.status),
                    isCustomer: false
                };

                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Supplier updated successfully!');
                    this.searchSuppliers();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update supplier'));
                }
            } catch (error) {
                console.error('Failed to update supplier:', error);
            }
        },

        editSupplier(supplier) {
            this.isEditing = true;
            this.form = {
                id: supplier.id,
                code: supplier.code,
                name: supplier.name,
                address: supplier.address,
                status: supplier.status.code,
                customer: false
            };
        },

        resetForm() {
            this.form = { id: null, code: '', name: '', address: '', status: 1, customer: false };
            this.isEditing = false;
        }
    }
};