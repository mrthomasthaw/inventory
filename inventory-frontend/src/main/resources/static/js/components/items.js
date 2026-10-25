const ItemView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Item Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <button @click="searchItems" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                🔄 Refresh
            </button>
        </header>

        <!-- Search & Filter Card -->
        <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-200 mb-6">
            <form @submit.prevent="handleSearch" class="grid grid-cols-1 md:grid-cols-6 gap-4 items-end">
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Code</label>
                    <input type="text" v-model="searchQuery.itemCode" placeholder="Search code..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Name</label>
                    <input type="text" v-model="searchQuery.itemName" placeholder="Search name..."
                           class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Main Category</label>
                    <select v-model.number="searchQuery.mainCategoryId"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                        <option value="">All Categories</option>
                        <option v-for="cat in mainCategories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
                    </select>
                </div>
                <div>
                    <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Unit</label>
                    <select v-model.number="searchQuery.unitId"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                        <option value="">All Units</option>
                        <option v-for="u in units" :key="u.id" :value="u.id">{{ u.name }}</option>
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
                    <button type="submit" class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-3 rounded-lg text-sm transition shadow-sm">
                        Search
                    </button>
                    <button type="button" @click="resetSearch" class="bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium py-2 px-2 rounded-lg text-sm transition">
                        Reset
                    </button>
                </div>
            </form>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

            <!-- Form Panel (Create / Edit Item) -->
            <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-200 h-fit">
                <h2 class="text-lg font-semibold text-gray-900 mb-4">
                    {{ isEditing ? 'Edit Item' : 'Create Item' }}
                </h2>

                <form @submit.prevent="submitForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Item Code *</label>
                        <input type="text" v-model="form.itemCode" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. ITEM-001">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Item Name *</label>
                        <input type="text" v-model="form.itemName" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                               placeholder="e.g. Wireless Mouse">
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Main Category *</label>
                        <select v-model.number="form.mainCategoryId" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option value="" disabled>Select Category</option>
                            <option v-for="cat in mainCategories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Unit *</label>
                        <select v-model.number="form.unitId" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option value="" disabled>Select Unit</option>
                            <option v-for="u in units" :key="u.id" :value="u.id">{{ u.name }}</option>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Description</label>
                        <textarea v-model="form.description" rows="2"
                                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none transition"
                                  placeholder="Enter description..."></textarea>
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
                            {{ isEditing ? 'Save Changes' : 'Create Item' }}
                        </button>
                        <button type="button" v-if="isEditing" @click="resetForm"
                                class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 px-4 rounded-lg transition">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>

            <!-- Item Table Panel with Pagination -->
            <div class="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Item List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ totalCount }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">Code</th>
                                    <th class="p-3">Name</th>
                                    <th class="p-3">Category</th>
                                    <th class="p-3">Unit</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="items.length === 0">
                                    <td colspan="8" class="text-center py-12 text-gray-400">
                                        No items found.
                                    </td>
                                </tr>
                                <tr v-for="(item, index) in items" :key="item.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ (currentPage * pageSize) + index + 1 }}</td>
                                    <td class="p-3 font-mono font-medium text-indigo-600">{{ item.itemCode }}</td>
                                    <td class="p-3 text-gray-900 font-medium">{{ item.itemName }}</td>
                                    <td class="p-3 text-gray-600">{{ item.mainCategory || '-' }}</td>
                                    <td class="p-3 text-gray-600">{{ item.unit || '-' }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': getStatusCode(item.status) === 1,
                                                  'bg-red-100 text-red-800': getStatusCode(item.status) === 2
                                              }">
                                            {{ getStatusLabel(item.status) }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(item.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editItem(item)" 
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
            items: [],
            mainCategories: [],
            units: [],
            totalCount: 0,
            currentPage: 0,
            pageSize: 10,
            totalPages: 1,
            isEditing: false,
            searchQuery: {
                itemCode: '',
                itemName: '',
                mainCategoryId: '',
                unitId: '',
                status: ''
            },
            form: {
                id: null,
                itemCode: '',
                itemName: '',
                description: '',
                mainCategoryId: '',
                unitId: '',
                status: 1
            },
            backendUrl: 'http://localhost:8081/api/v1/items',
            categoryUrl: 'http://localhost:8081/api/v1/main-categories',
            unitUrl: 'http://localhost:8081/api/v1/units'
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
        this.fetchDropdownData();
        this.searchItems();
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

        async fetchDropdownData() {
            try {
                const [catRes, unitRes] = await Promise.all([
                    fetch(this.categoryUrl),
                    fetch(this.unitUrl)
                ]);

                const catResult = await catRes.json();
                if (catRes.ok || catResult.code === 91) {
                    this.mainCategories = catResult.data || [];
                }

                const unitResult = await unitRes.json();
                if (unitRes.ok || unitResult.code === 91) {
                    this.units = unitResult.data || [];
                }
            } catch (error) {
                console.error('Failed to fetch dropdown options:', error);
            }
        },

        async searchItems() {
            try {
                const payload = {
                    itemCode: this.searchQuery.itemCode || null,
                    itemName: this.searchQuery.itemName || null,
                    mainCategoryId: this.searchQuery.mainCategoryId !== '' ? parseInt(this.searchQuery.mainCategoryId) : null,
                    unitId: this.searchQuery.unitId !== '' ? parseInt(this.searchQuery.unitId) : null,
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
                    this.items = pageResult.list || [];
                    this.totalCount = pageResult.totalCount || 0;
                    this.totalPages = Math.ceil(this.totalCount / this.pageSize) || 1;
                }
            } catch (error) {
                console.error('Failed to search items:', error);
            }
        },

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        handleSearch() {
            this.currentPage = 0;
            this.searchItems();
        },

        resetSearch() {
            this.searchQuery = { itemCode: '', itemName: '', mainCategoryId: '', unitId: '', status: '' };
            this.currentPage = 0;
            this.searchItems();
        },

        changePage(direction) {
            this.currentPage += direction;
            this.searchItems();
        },

        async submitForm() {
            if (this.isEditing) {
                await this.updateItem();
            } else {
                await this.createItem();
            }
        },

        async createItem() {
            try {
                const payload = {
                    itemCode: this.form.itemCode,
                    itemName: this.form.itemName,
                    description: this.form.description,
                    mainCategoryId: parseInt(this.form.mainCategoryId),
                    unitId: parseInt(this.form.unitId),
                    status: parseInt(this.form.status)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Item created successfully!');
                    this.searchItems();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create item'));
                }
            } catch (error) {
                console.error('Failed to create item:', error);
            }
        },

        async updateItem() {
            try {
                const payload = {
                    id: this.form.id,
                    itemCode: this.form.itemCode,
                    itemName: this.form.itemName,
                    description: this.form.description,
                    mainCategoryId: parseInt(this.form.mainCategoryId),
                    unitId: parseInt(this.form.unitId),
                    status: parseInt(this.form.status)
                };

                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Item updated successfully!');
                    this.searchItems();
                    this.resetForm();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update item'));
                }
            } catch (error) {
                console.error('Failed to update item:', error);
            }
        },

        editItem(item) {
            this.isEditing = true;
            // Find category ID and unit ID from loaded lists based on names if needed,
            // or match directly if your item response includes IDs. Assuming names matched:
            const matchedCategory = this.mainCategories.find(c => c.name === item.mainCategory);
            const matchedUnit = this.units.find(u => u.name === item.unit);

            this.form = {
                id: item.id,
                itemCode: item.itemCode,
                itemName: item.itemName,
                description: item.description,
                mainCategoryId: matchedCategory ? matchedCategory.id : '',
                unitId: matchedUnit ? matchedUnit.id : '',
                status: this.getStatusCode(item.status) ?? 1
            };
        },

        resetForm() {
            this.form = { id: null, itemCode: '', itemName: '', description: '', mainCategoryId: '', unitId: '', status: 1 };
            this.isEditing = false;
        }
    }
};