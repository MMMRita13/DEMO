using Azure.Core;
using pz19;
using pz19.Models;
using pz19.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using Request = pz19.Models.Request;

namespace pz19.ViewModels
{
    class RequestViewModel: BindableBase
    {
        private IRequestRepository _repository;
        public RequestViewModel(IRequestRepository repository)
        {

            _repository = repository;
            Requests = new ObservableCollection<Request>();
            LoadOrders();
            CloseCommand = new RelayCommand(Close);
        }
        private Client _selectedClient;

        public Client SelectedClient
        {
            get => _selectedClient;
            set
            {
                SetProperty(ref _selectedClient, value);
            }
        }
        private ObservableCollection<Request> _requests;
        public ObservableCollection<Request> Requests
        {
            get => _requests;
            set => SetProperty(ref _requests, value);
        }

        public RelayCommand CloseCommand { get; }

        public event Action? Done;

        public void Close()
        {
            Done?.Invoke();
        }

        public async Task LoadOrders()
        {
            var orders = await _repository.GetRequestsByClientAsync(SelectedClient.ClientId);
            Requests.Clear();
            foreach (var order in orders)
            {
                Requests.Add(order);
            }
        }

        public async Task LoadAllOrders()
        {

            if (SelectedClient == null)
            {
                var orders = await _repository.GetAllRequestsAsync();
                Requests.Clear();
                foreach (var order in orders)
                {
                    Requests.Add(order);
                }
            }
        }

        public event Action<Request> PlaceRequested = delegate { };

        private void OnPlace(Request request)
        {
            PlaceRequested(request);
        }
    }
}
