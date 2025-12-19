import java.util.ArrayList;


/**
 * Controller class that coordinates between Model and View
 */
public class HmsController {
    private HmsModel model;
    private HmsControllerView view;

    public HmsController(HmsModel model, HmsView view) {
        this.model = model;
        this.view = view;

        initializeView();
        setupEventListeners();
    }

    private void initializeView() {
        view.refreshStockTable(model.getAllStock(), model);
        view.refreshOrdersTable(model.getAllOrders(), model);
        view.refreshSuppliersTable(model.getAllSuppliers());
        view.refreshSupplierOrdersTable(model.getAllSupplierOrders(), model);
    }

    private void setupEventListeners() {
        view.setRefreshStockListener(new Runnable() {
            public void run() {
                handleRefreshStock();
            }
        });

        view.setShowLowStockListener(new Runnable() {
            public void run() {
                handleShowLowStock();
            }
        });

        view.setAddStockListener(new StockListener() {
            public void onAddStock(String isbn, int quantity, int reorderLevel, String supplierId) {
                handleAddStock(isbn, quantity, reorderLevel, supplierId);
            }
        });

        view.setUpdateStockListener(new UpdateStockListener() {
            public void onUpdateStock(String isbn, int newQuantity) {
                handleUpdateStock(isbn, newQuantity);
            }
        });

        view.setRefreshOrdersListener(new Runnable() {
            public void run() {
                handleRefreshOrders();
            }
        });

        view.setShowPendingOrdersListener(new Runnable() {
            public void run() {
                handleShowPendingOrders();
            }
        });

        view.setPlaceOrderListener(new OrderListener() {
            public void onPlaceOrder(String isbn, int quantity, String customerName) {
                handlePlaceOrder(isbn, quantity, customerName);
            }
        });

        view.setCompleteOrderListener(new CompleteOrderListener() {
            public void onCompleteOrder(String orderId) {
                handleCompleteOrder(orderId);
            }
        });

        view.setAddSupplierListener(new SupplierListener() {
            public void onAddSupplier(String name, String contactPerson, String phone, String email) {
                handleAddSupplier(name, contactPerson, phone, email);
            }
        });

        view.setRefreshSupplierOrdersListener(new Runnable() {
            public void run() {
                handleRefreshSupplierOrders();
            }
        });

        view.setShowPendingSupplierOrdersListener(new Runnable() {
            public void run() {
                handleShowPendingSupplierOrders();
            }
        });

        view.setCreateSupplierOrderListener(new CreateSupplierOrderListener() {
            public void onCreateSupplierOrder(String supplierId, String isbn, int quantity) {
                handleCreateSupplierOrder(supplierId, isbn, quantity);
            }
        });

        view.setReceiveSupplierOrderListener(new ReceiveSupplierOrderListener() {
            public void onReceiveSupplierOrder(String supplierOrderId) {
                handleReceiveSupplierOrder(supplierOrderId);
            }
        });

        view.setAddBookListener(new BookListener() {
            public void onAddBook(String isbn, String title, double price, String category, ArrayList<Author> authors) {
                handleAddBook(isbn, title, price, category, authors);
            }
        });

        view.setAddAuthorListener(new AuthorListener() {
            public void onAddAuthor(String name, String email) {
                handleAddAuthor(name, email);
            }
        });

        view.setOnCloseListener(new Runnable() {
            public void run() {
                handleSaveData();
            }
        });
    }

    // ========== Stock Management Handlers ==========

    private void handleRefreshStock() {
        view.refreshStockTable(model.getAllStock(), model);
    }

    private void handleShowLowStock() {
        view.showLowStockTable(model.getLowStock(), model);
    }

    private void handleAddStock(String isbn, int quantity, int reorderLevel, String supplierId) {
        Stock stock = new Stock(isbn, quantity, reorderLevel, supplierId);
        model.addStock(stock);
        view.showSuccessMessage("Stock item added successfully!");
        handleRefreshStock();
    }

    private void handleUpdateStock(String isbn, int newQuantity) {
        model.updateStockQuantity(isbn, newQuantity);
        view.showSuccessMessage("Stock quantity updated successfully!");
        handleRefreshStock();
    }

    // ========== Order Management Handlers ==========

    private void handleRefreshOrders() {
        view.refreshOrdersTable(model.getAllOrders(), model);
    }

    private void handleShowPendingOrders() {
        view.showPendingOrdersTable(model.getPendingOrders(), model);
    }

    private void handlePlaceOrder(String isbn, int quantity, String customerName) {
        boolean success = model.placeOrder(isbn, quantity, customerName);
        if (success) {
            view.showSuccessMessage("Order placed successfully!");
            handleRefreshOrders();
            handleRefreshStock();
        } else {
            view.showErrorMessage("Insufficient stock to place order!");
        }
    }

    private void handleCompleteOrder(String orderId) {
        model.completeOrder(orderId);
        view.showSuccessMessage("Order marked as completed!");
        handleRefreshOrders();
    }

    // ========== Supplier Management Handlers ==========

    private void handleAddSupplier(String name, String contactPerson, String phone, String email) {
        String supplierId = model.generateSupplierId();
        Supplier supplier = new Supplier(supplierId, name, contactPerson, phone, email);
        model.addSupplier(supplier);
        view.showSuccessMessage("Supplier added successfully!");
        view.refreshSuppliersTable(model.getAllSuppliers());
    }

    // ========== Supplier Order Management Handlers ==========

    private void handleRefreshSupplierOrders() {
        view.refreshSupplierOrdersTable(model.getAllSupplierOrders(), model);
    }

    private void handleShowPendingSupplierOrders() {
        view.showPendingSupplierOrdersTable(model.getPendingSupplierOrders(), model);
    }

    private void handleCreateSupplierOrder(String supplierId, String isbn, int quantity) {
        model.createSupplierOrder(supplierId, isbn, quantity);
        view.showSuccessMessage("Supplier order created successfully!");
        handleRefreshSupplierOrders();
    }

    private void handleReceiveSupplierOrder(String supplierOrderId) {
        model.receiveSupplierOrder(supplierOrderId);
        view.showSuccessMessage("Supplier order received! Stock updated.");
        handleRefreshSupplierOrders();
        handleRefreshStock();
    }

    // ========== Book Management Handlers ==========

    private void handleAddBook(String isbn, String title, double price, String category, ArrayList<Author> authors) {
        Book book = new Book(isbn, title, price, category);
        for (int i = 0; i < authors.size(); i++) {
            book.addAuthor(authors.get(i));
        }
        model.addBook(book);
        view.showSuccessMessage("Book added successfully!");
    }

    // ========== Author Management Handlers ==========

    private void handleAddAuthor(String name, String email) {
        String authorId = model.generateAuthorId();
        Author author = new Author(authorId, name, email);
        model.addAuthor(author);
        view.showSuccessMessage("Author added successfully!");
    }

    // ========== Data Persistence ==========

    private void handleSaveData() {
        model.saveAllData();
    }

    // ========== Data Access Methods for View ==========

    public ArrayList<Book> getAllBooks() {
        return model.getAllBooks();
    }

    public ArrayList<Author> getAllAuthors() {
        return model.getAllAuthors();
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return model.getAllSuppliers();
    }

    public ArrayList<Stock> getAllStock() {
        return model.getAllStock();
    }

    public Book getBook(String isbn) {
        return model.getBook(isbn);
    }

    public Supplier getSupplier(String supplierId) {
        return model.getSupplier(supplierId);
    }

    public Stock getStock(String isbn) {
        return model.getStock(isbn);
    }
}

// ========== Event Listener Interfaces ==========

interface StockListener {
    void onAddStock(String isbn, int quantity, int reorderLevel, String supplierId);
}

interface UpdateStockListener {
    void onUpdateStock(String isbn, int newQuantity);
}

interface OrderListener {
    void onPlaceOrder(String isbn, int quantity, String customerName);
}

interface CompleteOrderListener {
    void onCompleteOrder(String orderId);
}

interface SupplierListener {
    void onAddSupplier(String name, String contactPerson, String phone, String email);
}

interface CreateSupplierOrderListener {
    void onCreateSupplierOrder(String supplierId, String isbn, int quantity);
}

interface ReceiveSupplierOrderListener {
    void onReceiveSupplierOrder(String supplierOrderId);
}

interface BookListener {
    void onAddBook(String isbn, String title, double price, String category, ArrayList<Author> authors);
}

interface AuthorListener {
    void onAddAuthor(String name, String email);
}
