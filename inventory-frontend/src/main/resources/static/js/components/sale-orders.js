const SaleOrderView = {
    template: `
    <div class="max-w-7xl mx-auto p-6">
        
        <!-- Header -->
        <header class="flex justify-between items-center mb-6 pb-4 border-b border-gray-200">
            <div>
                <h1 class="text-2xl font-bold text-gray-900">Sale Order Management</h1>
                <p class="text-sm text-gray-500">Inventory System — Master Data</p>
            </div>
            <div class="flex space-x-3">
                <button v-if="!isFormOpen" @click="openCreateForm" class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg shadow-sm text-sm font-medium transition flex items-center space-x-1">
                    <span>➕ Create Sale Order</span>
                </button>
                <button v-if="isFormOpen" @click="closeForm" class="px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-700 rounded-lg shadow-sm text-sm font-medium transition flex items-center space-x-1">
                    <span>← Back to List</span>
                </button>
                <button @click="searchSaleOrders" class="px-4 py-2 bg-white border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 transition">
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
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">SO Number</label>
                        <input type="text" v-model="searchQuery.saleOrderNo" placeholder="Search SO No..."
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-indigo-500 outline-none">
                    </div>
                    <div>
                        <label class="block text-xs font-semibold text-gray-600 uppercase mb-1">Customer</label>
                        <select v-model.number="searchQuery.customerId"
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm bg-white focus:ring-2 focus:ring-indigo-500 outline-none">
                            <option value="">All Customer</option>
                            <option v-for="cust in customers" :key="cust.id" :value="cust.id">{{ cust.name }}</option>
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

            <!-- Sale Order Table Panel with Pagination -->
            <div class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden flex flex-col justify-between">
                <div>
                    <div class="p-4 border-b border-gray-100 flex justify-between items-center bg-gray-50">
                        <h2 class="font-semibold text-gray-800">Sale Order List</h2>
                        <span class="text-xs font-medium px-2.5 py-1 bg-indigo-100 text-indigo-800 rounded-full">
                            Total: {{ totalCount }}
                        </span>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-left border-collapse text-sm">
                            <thead>
                                <tr class="bg-gray-50 border-b border-gray-200 text-gray-500 font-medium">
                                    <th class="p-3 pl-4">No</th>
                                    <th class="p-3">SO No</th>
                                    <th class="p-3">Customer</th>
                                    <th class="p-3">Date</th>
                                    <th class="p-3">Total Qty</th>
                                    <th class="p-3">Total Amount (MMK)</th>
                                    <th class="p-3">Status</th>
                                    <th class="p-3">Created At</th>
                                    <th class="p-3 pr-4 text-right">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="saleOrders.length === 0">
                                    <td colspan="9" class="text-center py-12 text-gray-400">
                                        No sale orders found.
                                    </td>
                                </tr>
                                <tr v-for="(so, index) in saleOrders" :key="so.id" class="hover:bg-gray-50 transition">
                                    <td class="p-3 pl-4 font-mono text-xs text-gray-500">{{ (currentPage * pageSize) + index + 1 }}</td>
                                    <td class="p-3 font-mono font-medium text-indigo-600">{{ so.saleOrderNo || '-' }}</td>
                                    <td class="p-3 text-gray-900 font-medium">{{ so.customerName || '-' }}</td>
                                    <td class="p-3 text-gray-600">{{ so.saleOrderDate || '-' }}</td>
                                    <td class="p-3 text-gray-600 font-mono">{{ so.totalQty || '0.00' }}</td>
                                    <td class="p-3 text-gray-900 font-semibold font-mono">{{ so.totalAmount ? 'MMK ' + so.totalAmount.toFixed(2) : 'MMK 0.00' }}</td>
                                    <td class="p-3">
                                        <span class="px-2.5 py-1 text-xs font-semibold rounded-full"
                                              :class="{
                                                  'bg-green-100 text-green-800': getStatusCode(so.formStatus) === 1,
                                                  'bg-blue-100 text-blue-800': getStatusCode(so.formStatus) === 2,
                                                  'bg-purple-100 text-purple-800': getStatusCode(so.formStatus) === 3,
                                                  'bg-amber-100 text-amber-800': getStatusCode(so.formStatus) === 21
                                              }">
                                            {{ getStatusLabel(so.formStatus) }}
                                        </span>
                                    </td>
                                    <td class="p-3 text-xs text-gray-500">{{ formatDate(so.createdTime) }}</td>
                                    <td class="p-3 pr-4 text-right">
                                        <button @click="editSaleOrder(so)" 
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
                    {{ isEditing ? 'Edit Sale Order (' + form.saleOrderNo + ')' : 'Create New Sale Order' }}
                </h2>
                <button @click="closeForm" class="text-sm text-gray-500 hover:text-gray-700">✕ Cancel</button>
            </div>

            <form @submit.prevent="submitForm" class="space-y-6">
                <!-- Master Info Grid -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 bg-gray-50 p-4 rounded-xl border border-gray-200">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Customer *</label>
                        <select v-model.number="form.customerId" required
                                class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                            <option value="" disabled>Select Customer</option>
                            <option v-for="cust in customers" :key="cust.id" :value="cust.id">{{ cust.name }}</option>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Sale Order Date *</label>
                        <input type="date" v-model="form.saleOrderDate" required
                               class="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:ring-2 focus:ring-indigo-500 outline-none transition">
                    </div>
                </div>

                <!-- Item List Section -->
                <div>
                    <div class="flex justify-between items-center mb-3">
                        <h3 class="text-md font-semibold text-gray-800">Sale Order Items *</h3>
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
                                    <th class="p-3 w-36">Price (MMK)</th>
                                    <th class="p-3 w-36">Quantity</th>
                                    <th class="p-3 w-40">Total Amount</th>
                                    <th class="p-3 pr-4 w-20 text-right">Action</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-gray-100">
                                <tr v-if="form.itemList.length === 0">
                                    <td colspan="6" class="text-center py-8 text-gray-400">
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
                                        <input type="number" step="0.01" min="0.01" v-model.number="row.price" required
                                               class="w-full px-3 py-1.5 border border-gray-300 rounded-lg text-sm font-mono focus:ring-2 focus:ring-indigo-500 outline-none"
                                               placeholder="0.00">
                                    </td>
                                    <td class="p-3">
                                        <input type="number" step="0.01" min="0.01" v-model.number="row.qty" required
                                               class="w-full px-3 py-1.5 border border-gray-300 rounded-lg text-sm font-mono focus:ring-2 focus:ring-indigo-500 outline-none"
                                               placeholder="1.00">
                                    </td>
                                    <td class="p-3 font-mono font-semibold text-gray-800">
                                        {{ 'MMK ' + calculateRowTotal(row).toFixed(2) }}
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

                    <!-- Grand Totals Summary Box -->
                    <div class="mt-4 flex justify-end">
                        <div class="bg-gray-50 p-4 rounded-xl border border-gray-200 w-72 space-y-2 text-sm">
                            <div class="flex justify-between text-gray-600">
                                <span>Total Quantity:</span>
                                <span class="font-mono font-medium">{{ computedTotalQty.toFixed(2) }}</span>
                            </div>
                            <div class="flex justify-between text-gray-900 font-bold text-base border-t border-gray-200 pt-2">
                                <span>Grand Total:</span>
                                <span class="font-mono text-indigo-600">{{ 'MMK ' + computedGrandTotal.toFixed(2) }}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Form Actions -->
                <div class="flex items-center space-x-3 pt-4 border-t border-gray-200">
                    <button type="submit" 
                            class="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2.5 px-6 rounded-lg shadow-sm transition">
                        {{ isEditing ? 'Save Changes' : 'Create Sale Order' }}
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
            saleOrders: [],
            customers: [],
            itemsList: [],
            totalCount: 0,
            currentPage: 0,
            pageSize: 10,
            totalPages: 1,
            isFormOpen: false,
            isEditing: false,
            searchQuery: {
                saleOrderNo: '',
                customerId: '',
                fromDate: '',
                toDate: '',
                formStatus: ''
            },
            form: {
                id: null,
                saleOrderNo: '',
                customerId: '',
                saleOrderDate: new Date().toISOString().split('T')[0],
                itemList: []
            },
            backendUrl: 'http://localhost:8081/api/v1/sale-orders',
            customerUrl: 'http://localhost:8081/api/v1/business-partners',
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
            return this.form.itemList.reduce((sum, row) => sum + (Number(row.qty) || 0), 0);
        },
        computedGrandTotal() {
            return this.form.itemList.reduce((sum, row) => sum + this.calculateRowTotal(row), 0);
        }
    },
    mounted() {
        this.fetchCustomers();
        this.fetchItems();
        this.searchSaleOrders();
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

        async fetchCustomers() {
            try {
                const response = await fetch(`${this.customerUrl}/search`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({customer: true})
                });
                const result = await response.json();
                if (response.ok || result.code === 91) {
                    this.customers = result.data.list || [];
                }
            } catch (error) {
                console.error('Failed to fetch customer:', error);
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

        async searchSaleOrders() {
            try {
                const payload = {
                    saleOrderNo: this.searchQuery.saleOrderNo || null,
                    customerId: this.searchQuery.customerId !== '' ? parseInt(this.searchQuery.customerId) : null,
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
                    this.saleOrders = pageResult.list || [];
                    this.totalCount = pageResult.totalCount || 0;
                    this.totalPages = Math.ceil(this.totalCount / this.pageSize) || 1;
                }
            } catch (error) {
                console.error('Failed to search sale orders:', error);
            }
        },

        formatDate(dateString) {
            if (!dateString) return '-';
            const date = new Date(dateString);
            return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        },

        handleSearch() {
            this.currentPage = 0;
            this.searchSaleOrders();
        },

        resetSearch() {
            this.searchQuery = { purchaseOrderNo: '', supplierId: '', fromDate: '', toDate: '', formStatus: '' };
            this.currentPage = 0;
            this.searchSaleOrders();
        },

        changePage(direction) {
            this.currentPage += direction;
            this.searchPurchaseOrders();
        },

        openCreateForm() {
            this.isEditing = false;
            this.form = {
                id: null,
                saleOrderNo: '',
                customerId: '',
                saleOrderDate: new Date().toISOString().split('T')[0],
                itemList: [{ itemId: '', price: 0, qty: 1 }],
                requestId: crypto.randomUUID()
            };
            this.isFormOpen = true;
        },

        async editSaleOrder(so) {
           try {
               this.isEditing = true;

               const response = await fetch(`${this.backendUrl}/${so.id}`, {
                   method: 'GET',
                   headers: {'Content-Type': 'application/json'}
               });

               const result = await response.json();

               if(response.ok) {
                   const data = result.data;

                   console.log(data);
                   this.form = {
                       id: data.id,
                       saleOrderNo: data.saleOrderNo,
                       customerId: data.customerId,
                       saleOrderDate: data.saleOrderDate || new Date().toISOString().split('T')[0],
                       itemList: data.itemList?.length ? data.itemList : [{ itemId: '', price: 0, qty: 1 }]
                   };
                   this.isFormOpen = true;
               }

           }
           catch (error) {
               console.error("Failed to fetch sale order");
           }
        },

        closeForm() {
            this.isFormOpen = false;
        },

        addItemRow() {
            this.form.itemList.push({ itemId: '', price: 0, qty: 1 });
        },

        removeItemRow(index) {
            this.form.itemList.splice(index, 1);
        },

        calculateRowTotal(row) {
            const price = Number(row.price) || 0;
            const qty = Number(row.qty) || 0;
            return price * qty;
        },

        async submitForm() {
            if (this.form.itemList.length === 0) {
                this.$toast('Please add at least one item to the sale order.', 'error');
                return;
            }

            if (this.isEditing) {
                await this.updateSaleOrder();
            } else {
                await this.createSaleOrder();
            }
        },

        async createSaleOrder() {
            try {
                const payload = {
                    saleOrderDate: this.form.saleOrderDate,
                    customerId: parseInt(this.form.customerId),
                    itemList: this.form.itemList.map(item => ({
                        itemId: parseInt(item.itemId),
                        price: Number(item.price),
                        qty: Number(item.qty)
                    }))
                };

                const response = await fetch(this.backendUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-Request-Id': this.form.requestId
                    },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    this.$toast('Sale Order created successfully!', 'success');
                    this.isFormOpen = false;
                    this.searchSaleOrders();
                } else {
                    this.$toast(result.message || 'Failed to create sale order', 'error');
                }
            } catch (error) {
                console.error('Failed to create sale order:', error);
            }
        },

        async updateSaleOrder() {
            try {
                const payload = {
                    id: this.form.id,
                    saleOrderDate: this.form.saleOrderDate,
                    customerId: parseInt(this.form.customerId),
                    itemList: this.form.itemList.map(item => ({
                        itemId: parseInt(item.itemId),
                        price: Number(item.price),
                        qty: Number(item.qty)
                    }))
                };

                const response = await fetch(`${this.backendUrl}`, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                const result = await response.json();
                if (response.ok) {
                    this.$toast('Sale Order updated successfully!');
                    this.isFormOpen = false;
                    this.searchSaleOrders();
                } else {
                    this.$toast('Error: ' + (result.message || 'Failed to update sale order'));
                }
            } catch (error) {
                console.error('Failed to update sale order:', error);
            }
        }
    }
};