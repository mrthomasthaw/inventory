const GoodsReceivedView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Goods Received Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <div class="flex space-x-3">
                <button v-if="!isFormOpen" @click="openCreateForm" class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg shadow-sm text-sm font-medium transition flex items-center space-x-1">
                    <span>➕ Create Goods Received</span>
                </button>
                <button v-if="isFormOpen" @click="closeForm" class="px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg shadow-sm text-sm font-medium transition flex items-center space-x-1">
                    <span>← Back to List</span>
                </button>
                <button @click="searchGoodsReceived" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
                    🔄 Refresh
                </button>
            </div>
        </header>

        <!-- ================= SEARCH & LIST VIEW ================= -->
        <div v-if="!isFormOpen">
            <!-- Search & Filter Card -->
            <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-200 mb-6">
                <form @submit.prevent="handleSearch" class="grid grid-cols-1 md:grid-cols-6 gap-4 items-end">
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">GR No</label>
                        <input type="text" v-model="searchQuery.goodsReceivedNo" placeholder="Search GR No..."
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Purchase Order</label>
                        <select v-model.number="searchQuery.purchaseOrderId"
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                            <option value="">All Purchase Orders</option>
                            <option v-for="po in purchaseOrdersList" :key="po.id" :value="po.id">{{ po.purchaseOrderNo }}</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">From Date</label>
                        <input type="date" v-model="searchQuery.fromDate"
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">To Date</label>
                        <input type="date" v-model="searchQuery.toDate"
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Status</label>
                        <select v-model.number="searchQuery.formStatus"
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                            <option value="">All Statuses</option>
                            <option :value="1">Ready To Process</option>
                            <option :value="2">Processing</option>
                            <option :value="3">Complete</option>
                            <option :value="21">Draft</option>
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

            <!-- Goods Received Table Panel with Pagination -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Goods Received List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ totalCount }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">GR No</th>
                                    <th class="p-3">Purchase Order ID</th>
                                    <th class="p-3">Received Date</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="goodsReceivedList.length === 0">
                                    <td colspan="7" class="text-center py-12 text-gray-400">
                                        No goods received records found.
                                    </td>
                                </tr>
                                <tr v-for="(gr, index) in goodsReceivedList" :key="gr.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ (currentPage * pageSize) + index + 1 }}</td>
                                    <td class="p-3 font-mono font-medium text-indigo-600">{{ gr.goodsReceivedNo || '-' }}</td>
                                    <td class="p-3 text-gray-900 font-medium">PO ID: {{ gr.purchaseOrderId || '-' }}</td>
                                    <td class="p-3 text-gray-600">{{ gr.goodsReceivedDate || '-' }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': getStatusCode(gr.formStatus) === 1,
                                                  'bg-blue-100 text-blue-800': getStatusCode(gr.formStatus) === 2,
                                                  'bg-purple-100 text-purple-800': getStatusCode(gr.formStatus) === 3,
                                                  'bg-amber-100 text-amber-800': getStatusCode(gr.formStatus) === 21
                                              }">
                                            {{ getStatusLabel(gr.formStatus) }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(gr.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editGoodsReceived(gr)" 
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

        <!-- ================= DEDICATED CREATE / EDIT FORM VIEW ================= -->
        <div v-if="isFormOpen" class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
            <div class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
                <h2 class="text-xl font-bold text-gray-900">
                    {{ isEditing ? 'Edit Goods Received (' + form.goodsReceivedNo + ')' : 'Create New Goods Received' }}
                </h2>
                <button @click="closeForm" class="text-sm text-gray-500 hover:text-gray-700">✕ Cancel</button>
            </div>

            <form @submit.prevent="submitForm" class="space-y-6">
                <!-- Master Info Grid -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 bg-gray-50 p-4 rounded-xl border border-gray-200">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Purchase Order *</label>
                        <select v-model.number="form.purchaseOrderId" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option value="" disabled>Select Purchase Order</option>
                            <option v-for="po in purchaseOrdersList" :key="po.id" :value="po.id">
                                {{ po.purchaseOrderNo }} (ID: {{ po.id }})
                            </option>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Goods Received Date *</label>
                        <input type="date" v-model="form.goodsReceivedDate" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                    </div>
                </div>

                <!-- Item List Section -->
                <div>
                    <div class="flex justify-between items-center mb-3">
                        <h3 class="text-md font-semibold text-gray-800">Goods Received Items *</h3>
                        <button type="button" @click="addItemRow" class="px-3 py-1.5 bg-indigo-50 hover:bg-indigo-100 text-indigo-700 rounded-lg text-sm font-medium transition border border-indigo-200">
                            ➕ Add Item Row
                        </button>
                    </div>

                    <div class="border border-gray-200 rounded-xl overflow-hidden">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4 w-12">No</th>
                                    <th class="p-3">Item</th>
                                    <th class="p-3 w-48">Received Quantity</th>
                                    <th class="p-3 pr-4 w-20 text-right">Action</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="form.itemList.length === 0">
                                    <td colspan="4" class="text-center py-8 text-gray-400">
                                        No items added yet. Click "+ Add Item Row" to begin.
                                    </td>
                                </tr>
                                <tr v-for="(row, index) in form.itemList" :key="index" class="hover:bg-gray-50">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ index + 1 }}</td>
                                    <td class="p-3">
                                        <select v-model.number="row.itemId" required
                                                class="w-full px-3 py-1.5 border border-gray-300 rounded-lg bg-white text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                                            <option value="" disabled>Select Item</option>
                                            <option v-for="item in itemsList" :key="item.id" :value="item.id">
                                                {{ item.itemCode }} - {{ item.itemName }}
                                            </option>
                                        </select>
                                    </td>
                                    <td class="p-3">
                                        <input type="number" step="0.01" min="0.01" v-model.number="row.qty" required
                                               class="w-full px-3 py-1.5 border border-gray-300 rounded-lg text-sm font-mono focus:ring-2 focus:ring-indigo-500 outline-none"
                                               placeholder="1.00">
                                    </td>
                                    <td class="p-3 pr-4 text-right">
                                        <button type="button" @click="removeItemRow(index)" 
                                                class="text-xs bg-red-50 hover:bg-red-100 text-red-600 font-medium px-2.5 py-1.5 rounded-md transition border border-red-200">
                                            🗑️ Remove
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- Total Qty Summary Box -->
                    <div class="mt-4 flex justify-end">
                        <div class="bg-gray-50 p-4 rounded-xl border border-gray-200 w-72 space-y-2 text-sm">
                            <div class="flex justify-between text-gray-900 font-bold text-base">
                                <span>Total Received Qty:</span>
                                <span class="font-mono text-indigo-600">{{ computedTotalQty.toFixed(2) }}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Form Actions -->
                <div class="flex items-center space-x-3 pt-4 border-t border-gray-200">
                    <button type="submit" 
                            class="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2.5 px-6 rounded-lg shadow-sm transition">
                        {{ isEditing ? 'Save Changes' : 'Create Goods Received' }}
                    </button>
                    <button type="button" @click="closeForm"
                            class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2.5 px-6 rounded-lg transition">
                        Cancel
                    </button>
                </div>
            </form>
        </div>

    </div>
    `,
    data() {
        return {
            goodsReceivedList: [],
            purchaseOrdersList: [],
            itemsList: [],
            totalCount: 0,
            currentPage: 0,
            pageSize: 10,
            totalPages: 1,
            isFormOpen: false,
            isEditing: false,
            searchQuery: {
                goodsReceivedNo: '',
                purchaseOrderId: '',
                fromDate: '',
                toDate: '',
                formStatus: ''
            },
            form: {
                id: null,
                goodsReceivedNo: '',
                goodsReceivedDate: new Date().toISOString().split('T')[0],
                purchaseOrderId: '',
                itemList: []
            },
            backendUrl: 'http://localhost:8081/api/v1/goods-received',
            poUrl: 'http://localhost:8081/api/v1/purchase-orders',
            itemUrl: 'http://localhost:8081/api/v1/items'
        };
    },
    computed: {
        startItemIndex() {
            return this.totalCount === 0 ? 0 : (this.currentPage * this.pageSize) + 1;
        },
        endItemIndex() {
            return Math.min((this.currentPage + 1) * this.pageSize, this.totalCount);
        },
        computedTotalQty() {
            if (!this.form.itemList) return 0;
            return this.form.itemList.reduce((sum, row) => sum + (Number(row.qty) || 0), 0);
        }
    },
    mounted() {
        this.fetchPurchaseOrders();
        this.fetchItems();
        this.searchGoodsReceived();
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
            if (code === 1) return 'Ready To Process';
            if (code === 2) return 'Processing';
            if (code === 3) return 'Complete';
            if (code === 21) return 'Draft';
            return 'Unknown';
        },

        async fetchPurchaseOrders() {
            try {
                // Fetching list for dropdown selection
                const response = await fetch(`${this.poUrl}/search`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ page: 0, pageSize: 100 })
                });
                const result = await response.json();
                if (response.ok) {
                    this.purchaseOrdersList = result.data.list || [];
                }
            } catch (error) {
                console.error('Failed to fetch purchase orders list:', error);
            }
        },

        async fetchItems() {
            try {
                const response = await fetch(`${this.itemUrl}`);
                const result = await response.json();
                if (response.ok || result.code === 91) {
                    this.itemsList = result.data || [];
                }
            } catch (error) {
                console.error('Failed to fetch items list:', error);
            }
        },

        async searchGoodsReceived() {
            try {
                const payload = {
                    goodsReceivedNo: this.searchQuery.goodsReceivedNo || null,
                    purchaseOrderId: this.searchQuery.purchaseOrderId !== '' ? parseInt(this.searchQuery.purchaseOrderId) : null,
                    fromDate: this.searchQuery.fromDate || null,
                    toDate: this.searchQuery.toDate || null,
                    formStatus: this.searchQuery.formStatus !== '' ? parseInt(this.searchQuery.formStatus) : null,
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
                    this.goodsReceivedList = pageResult.list || [];
                    this.totalCount = pageResult.totalCount || 0;
                    this.totalPages = Math.ceil(this.totalCount / this.pageSize) || 1;
                }
            } catch (error) {
                console.error('Failed to search goods received:', error);
            }
        },

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        handleSearch() {
            this.currentPage = 0;
            this.searchGoodsReceived();
        },

        resetSearch() {
            this.searchQuery = { goodsReceivedNo: '', purchaseOrderId: '', fromDate: '', toDate: '', formStatus: '' };
            this.currentPage = 0;
            this.searchGoodsReceived();
        },

        changePage(direction) {
            this.currentPage += direction;
            this.searchGoodsReceived();
        },

        openCreateForm() {
            this.isEditing = false;
            this.form = {
                id: null,
                goodsReceivedNo: '',
                goodsReceivedDate: new Date().toISOString().split('T')[0],
                purchaseOrderId: '',
                itemList: [{ itemId: '', qty: 1 }]
            };
            this.isFormOpen = true;
        },

        async editGoodsReceived(gr) {
            try {
                this.isEditing = true;
                const response = await fetch(`${this.backendUrl}/${gr.id}`, {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' }
                });
                const result = await response.json();

                if (response.ok) {
                    const data = result.data || result;
                    this.form = {
                        id: data.id,
                        goodsReceivedNo: data.goodsReceivedNo,
                        goodsReceivedDate: data.goodsReceivedDate || new Date().toISOString().split('T')[0],
                        purchaseOrderId: data.purchaseOrderId,
                        itemList: (data.itemList && data.itemList.length > 0) ? data.itemList : [{ itemId: '', qty: 1 }]
                    };
                    this.isFormOpen = true;
                } else {
                    alert('Error: ' + (result.message || 'Failed to fetch goods received details'));
                    this.isEditing = false;
                }
            } catch (error) {
                console.error('Failed to fetch goods received details:', error);
                alert('An error occurred while loading goods received.');
                this.isEditing = false;
            }
        },

        closeForm() {
            this.isFormOpen = false;
        },

        addItemRow() {
            this.form.itemList.push({ itemId: '', qty: 1 });
        },

        removeItemRow(index) {
            this.form.itemList.splice(index, 1);
        },

        async submitForm() {
            if (this.form.itemList.length === 0) {
                alert('Please add at least one item row.');
                return;
            }

            if (this.isEditing) {
                await this.updateGoodsReceived();
            } else {
                await this.createGoodsReceived();
            }
        },

        async createGoodsReceived() {
            try {
                const payload = {
                    goodsReceivedDate: this.form.goodsReceivedDate,
                    purchaseOrderId: parseInt(this.form.purchaseOrderId),
                    itemList: this.form.itemList.map(item => ({
                        itemId: parseInt(item.itemId),
                        qty: Number(item.qty)
                    }))
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Goods Received created successfully!');
                    this.isFormOpen = false;
                    this.searchGoodsReceived();
                } else {
                    alert('Error: ' + (result.message || 'Failed to create goods received'));
                }
            } catch (error) {
                console.error('Failed to create goods received:', error);
            }
        },

        async updateGoodsReceived() {
            try {
                const payload = {
                    id: this.form.id,
                    goodsReceivedDate: this.form.goodsReceivedDate,
                    purchaseOrderId: parseInt(this.form.purchaseOrderId),
                    itemList: this.form.itemList.map(item => ({
                        id: item.id || null,
                        itemId: parseInt(item.itemId),
                        qty: Number(item.qty)
                    }))
                };

                const response = await fetch(this.backendUrl, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    alert('Goods Received updated successfully!');
                    this.isFormOpen = false;
                    this.searchGoodsReceived();
                } else {
                    alert('Error: ' + (result.message || 'Failed to update goods received'));
                }
            } catch (error) {
                console.error('Failed to update goods received:', error);
            }
        }
    }
};