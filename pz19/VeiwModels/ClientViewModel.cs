using Pizza.Services;
using pz19;
using pz19.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz_18.ViewModels
{
    class ClientViewModel: BindableBase
    {
        private IClientRepository _repository;
        public ClientViewModel(IClientRepository repository)
        {

            _repository = repository;
            Clients = new ObservableCollection<Client>();
            LoadCustomers();

            PlaceOrderCommand = new RelayCommand<Client>(OnPlaceOrder);
            AddCustomerCommand = new RelayCommand(OnAddCustomer);
            EditCustomerCommand = new RelayCommand<Client>(OnEditCustomer);
            ClearSearchInput = new RelayCommand(OnClearSearch);


            GetAllOrdersByCustomerCommand = new RelayCommand<Client>(OnGetAllOrdersByCustomer);

            LoadCustomersCommand = new RelayCommand(async () => await LoadCustomers());
        }

        private ObservableCollection<Client>? _customers;
        public ObservableCollection<Client>? Clients
        {
            get => _customers;
            set => SetProperty(ref _customers, value);
        }

        private List<Client>? _customersList;
        public async Task LoadCustomers()
        {
            _customersList = await _repository.GetClientsAsync();
            Clients = new ObservableCollection<Client>(_customersList);
        }

        private string? _searchInput;
        public string? SearchInput
        {
            get => _searchInput;
            set
            {
                SetProperty(ref _searchInput, value);
                FilterCustomersBuName(_searchInput);
            }
        }

        private void FilterCustomersBuName(string findText)
        {
            if (string.IsNullOrEmpty(findText))
            {
                Clients = new ObservableCollection<Client>(_customersList);
                return;
            }
            else
            {
                Clients = new ObservableCollection<Client>(
                    _customersList.Where(c => c.Fio.ToLower()
                    .Contains(findText.ToLower())));
            }
        }

        public RelayCommand<Client> PlaceOrderCommand { get; private set; }
        public RelayCommand AddCustomerCommand { get; private set; }
        public RelayCommand<Client> EditCustomerCommand { get; private set; }
        public RelayCommand ClearSearchInput { get; private set; }
        public RelayCommand LoadCustomersCommand { get; }
        public RelayCommand<Client> GetAllOrdersByCustomerCommand { get; private set; }
        public RelayCommand<Client> GetAllOrdersCommand { get; private set; }


        public event Action<Client> PlaceOrderRequested = delegate { };
        public event Action AddCustomerRequested = delegate { };
        public event Action<Client> EditCustomerRequested = delegate { };
        public event Action<Client> GetAllOrdersByCustomerRequested = delegate { };

        private void OnPlaceOrder(Client customer)
        {
            PlaceOrderRequested(customer);
        }

        private void OnAddCustomer()
        {
            AddCustomerRequested?.Invoke();
        }

        private void OnEditCustomer(Client customer)
        {
            EditCustomerRequested(customer);
        }

        private void OnClearSearch()
        {
            SearchInput = null;
        }
        private void OnGetAllOrdersByCustomer(Client customer)
        {
            GetAllOrdersByCustomerRequested(customer);
        }

    }
}
