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

            PlaceRequestCommand = new RelayCommand<Client>(OnPlaceRequest);
            AddClientCommand = new RelayCommand(OnAddClient);
            EditClientCommand = new RelayCommand<Client>(OnEditClient);
            ClearSearchInput = new RelayCommand(OnClearSearch);


            GetAllRequestsByClientCommand = new RelayCommand<Client>(OnGetAllRequestsByClient);

            LoadClientCommand = new RelayCommand(async () => await LoadClients());
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
                FilterClientsBuName(_searchInput);
            }
        }

        private void FilterClientsBuName(string findText)
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

        public RelayCommand<Client> PlaceRequestCommand { get; private set; }
        public RelayCommand AddClientCommand { get; private set; }
        public RelayCommand<Client> EditClientCommand { get; private set; }
        public RelayCommand ClearSearchInput { get; private set; }
        public RelayCommand LoadClientCommand { get; }
        public RelayCommand<Client> GetAllRequestsByClientCommand { get; private set; }
        public RelayCommand<Client> GetAllRequestsCommand { get; private set; }


        public event Action<Client> PlaceRequestRequested = delegate { };
        public event Action AddClientRequested = delegate { };
        public event Action<Client> EditClientRequested = delegate { };
        public event Action<Client> GetAllRequestsByClientRequested = delegate { };

        private void OnPlaceRequest(Client client)
        {
            PlaceRequestRequested(client);
        }

        private void OnAddClient()
        {
            AddClientRequested?.Invoke();
        }

        private void OnEditClient(Client client)
        {
            EditClientRequested(client);
        }

        private void OnClearSearch()
        {
            SearchInput = null;
        }
        private void OnGetAllRequestsByClient(Client client)
        {
            GetAllRequestsByClientRequested(client);
        }

    }
}
