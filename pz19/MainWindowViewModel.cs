using pz19.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows;

using Unity;
using pz19.ViewModels;
using pz19.Models;



namespace pz19
{
    class MainWindowViewModel : BindableBase
    {
        private AddEditClientViewModel _addEditClientVewModel;
        private ClientViewModel _clientViewModel;
        private AddRequestViewModel _addRequestViewModel;
        private RequestViewModel _requestViewModel;



        public MainWindowViewModel()
        {
            NavigationCommand = new RelayCommand<string>(OnNavigation);

            _clientViewModel = RepoContainer.Container.Resolve<ClientViewModel>();
            _addEditClientVewModel = RepoContainer.Container.Resolve<AddEditClientViewModel>();
            _addRequestViewModel = RepoContainer.Container.Resolve<AddRequestViewModel>();
//            _requestViewModel = new RequestViewModel(_requestRepository);
            _requestViewModel = RepoContainer.Container.Resolve<RequestViewModel>();


            _clientViewModel.PlaceRequestRequested += NavigateToRequest;

            _clientViewModel.AddClientRequested += NavigationToAddClient;
            _clientViewModel.EditClientRequested += NavigationToEditClient;

            _clientViewModel.GetAllRequestsByClientRequested += NavigateToRequests;

            _addRequestViewModel.Done += ReturnToClientList;
            _requestViewModel.Done += ReturnToClientList;

            _addEditClientVewModel.Done += OnDone;



        }

        private BindableBase _clientBBViewModel;

        public BindableBase ClientBBViewModel
        {
            get => _clientBBViewModel;
            set => SetProperty(ref _clientBBViewModel, value);
        }

        public RelayCommand<string> NavigationCommand { get; private set; }

        //открывать список клиентов
        private void OnNavigation(string dest)
        {
            switch (dest)
            {
                case "addRequest":
                    //_requestViewModel.SelectedClient = null;
                    //_requestViewModel.LoadAllRequests();
                    ClientBBViewModel = _requestViewModel;
                    break;
                case "clients":
                default:
                    ClientBBViewModel = _clientViewModel;
                    break;
            }
        }

        private void OnDone()
        {
            _ = _clientViewModel.LoadClients();
            ClientBBViewModel = _clientViewModel;
        }

        //открывать окно для редактирования клиента
        private void NavigationToEditClient(Client client)
        {
            _addEditClientVewModel.IsEditeMode = true;
            _addEditClientVewModel.SetClient(client);
            ClientBBViewModel = _addEditClientVewModel;
        }

        private void NavigationToEditRequest(Client client)
        {
            _addEditClientVewModel.IsEditeMode = true;
            _addEditClientVewModel.SetClient(client);
            ClientBBViewModel = _addEditClientVewModel;
        }

        private void NavigationToAddClient()
        {
            _addEditClientVewModel.IsEditeMode = false;
            _addEditClientVewModel.SetClient(new Client
            {
                
                ClientId = BitConverter.ToInt32(Guid.NewGuid().ToByteArray(), 0),
            });
            ClientBBViewModel = _addEditClientVewModel;
        }

        //окно для оформления заказа
        private void NavigateToRequest(Client? client)
        {
            if (client == null)
            {
                MessageBox.Show("Client is not selected. Please select a client to proceed.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            _addRequestViewModel.SelectedClient = client;
            _addRequestViewModel.Request = new Request
            {
                ClientId = client.ClientId,
                StartDate = DateTime.Now,
                CompletionDate = DateOnly.FromDateTime(DateTime.Now),
            };
            ClientBBViewModel = _addRequestViewModel;
        }
        private void NavigateToRequests(Client? client)
        {
            if (client == null)
            {
                MessageBox.Show("Client is not selected. Please select a client to proceed.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            //_requestViewModel.LoadRequests();
            _requestViewModel.SelectedClient = client;
            ClientBBViewModel = _requestViewModel;
        }
        private void ReturnToClientList()
        {
            ClientBBViewModel = _clientViewModel;
        }
    }
}
