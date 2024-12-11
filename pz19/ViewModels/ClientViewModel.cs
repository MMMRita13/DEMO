using pz19.Services;
using pz19;
using pz19.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz19.ViewModels
{
    class ClientViewModel: BindableBase
    {
        private IClientRepository _repository;
        public ClientViewModel(IClientRepository repository)
        {

            _repository = repository;
            Clients = new ObservableCollection<Client>();
            LoadClients();

            PlaceOrderCommand = new RelayCommand<Client>(OnPlaceOrder);
            AddCustomerCommand = new RelayCommand(OnAddCustomer);
            EditCustomerCommand = new RelayCommand<Client>(OnEditCustomer);
            ClearSearchInput = new RelayCommand(OnClearSearch);


            GetAllOrdersByCustomerCommand = new RelayCommand<Client>(OnGetAllOrdersByCustomer);

            LoadCustomersCommand = new RelayCommand(async () => await LoadClients());
        }

        private ObservableCollection<Client>? _clients;
        public ObservableCollection<Client>? Clients
        {
            get => _clients;
            set => SetProperty(ref _clients, value);
        }

        private List<Client>? _clientsList;
        public async Task LoadClients()
        {
            _clientsList = await _repository.GetClientsAsync();
            Clients = new ObservableCollection<Client>(_clientsList);
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
                Clients = new ObservableCollection<Client>(_clientsList);
                return;
            }
            else
            {
                Clients = new ObservableCollection<Client>(
                   _clientsList.Where(c => c.Fio.ToLower()
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

        private void OnPlaceOrder(Client client)
        {
            PlaceOrderRequested(client);
        }

        private void OnAddCustomer()
        {
            AddCustomerRequested?.Invoke();
        }

        private void OnEditCustomer(Client client)
        {
            EditCustomerRequested(client);
        }

        private void OnClearSearch()
        {
            SearchInput = null;
        }
        private void OnGetAllOrdersByCustomer(Client client)
        {
            GetAllOrdersByCustomerRequested(client);
        }

    }
}
