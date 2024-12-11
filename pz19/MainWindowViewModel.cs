using pz19.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows;

using Unity;
using pz_18.ViewModels;
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
            _requestViewModel = RepoContainer.Container.Resolve<RequestViewModel>();


            _clientViewModel.PlaceOrderRequested += NavigateToOrder;

            _clientViewModel.AddCustomerRequested += NavigationToAddCustomer;
            _clientViewModel.EditCustomerRequested += NavigationToEditCustomer;

            _clientViewModel.GetAllOrdersByCustomerRequested += NavigateToOrders;

            _addRequestViewModel.Done += ReturnToCustomerList;
            _requestViewModel.Done += ReturnToCustomerList;

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
                case "orderPrep":
                    ClientBBViewModel = _requestViewModel;
                    _requestViewModel.SelectedClient = null;
                    _ = _requestViewModel.LoadAllOrders();
                    break;
                case "customers":
                default:
                    ClientBBViewModel = _clientBBViewModel;
                    break;
            }
        }

        private void OnDone()
        {
            _ = _clientViewModel.LoadCustomers();
            ClientBBViewModel = _clientViewModel;
        }

        //открывать окно для редактирования клиента
        private void NavigationToEditCustomer(Client client)
        {
            _addEditClientVewModel.IsEditeMode = true;
            _addEditClientVewModel.SetClient(client);
            ClientBBViewModel = _addEditClientVewModel;
        }

        private void NavigationToEditOrder(Client client)
        {
            _addEditClientVewModel.IsEditeMode = true;
            _addEditClientVewModel.SetClient(client);
            ClientBBViewModel = _addEditClientVewModel;
        }

        private void NavigationToAddCustomer()
        {
            _addEditClientVewModel.IsEditeMode = false;
            _addEditClientVewModel.SetClient(new Client
            {
                ClientId = Guid.NewGuid(),
            });
            ClientBBViewModel = _addEditClientVewModel;
        }

        //окно для оформления заказа
        private void NavigateToOrder(Client? client)
        {
            if (client == null)
            {
                MessageBox.Show("Client is not selected. Please select a client to proceed.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            _addRequestViewModel.SelectedCustomer = client;
            _addRequestViewModel.Request = new Request
            {
                ClientId = client.ClientId,
                StartDate = DateTime.Now,
                CompletionDate = DateTime.Now.AddDays(1)
            };
            ClientBBViewModel = _addRequestViewModel;
        }
        private void NavigateToOrders(Client? client)
        {
            if (client == null)
            {
                MessageBox.Show("Client is not selected. Please select a client to proceed.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            _requestViewModel.LoadOrders();
            _requestViewModel.SelectedClient = client;
            ClientBBViewModel = _requestViewModel;
        }
        private void ReturnToCustomerList()
        {
            ClientBBViewModel = _clientViewModel;
        }
    }
}
