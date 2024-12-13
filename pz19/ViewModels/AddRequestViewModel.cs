
using pz19.Models;
using pz19.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;


namespace pz19.ViewModels
{
    class AddRequestViewModel: BindableBase
    {
        private IRequestRepository _orderRepository;
        public AddRequestViewModel(IRequestRepository orderRepository)
        {
            _orderRepository = orderRepository;

            Request = new Request
            {
                StartDate = DateTime.Now,
                CompletionDate = DateTime.Now.AddDays(1)
            };
            SaveRequestCommand = new RelayCommand(OnSaveRequest);
            CancelCommand = new RelayCommand(OnCancel);

            LoadOrderStatuses();
        }

        private Request _order;

        public Request Request
        {
            get => _order;
            set => SetProperty(ref _order, value);
        }

        private Client? _selectedClient;

        public Client? SelectedClient
        {
            get => _selectedClient;
            set
            {
                SetProperty(ref _selectedClient, value);
                Request.ClientId = value?.ClientId ?? 0;
            }
        }

        public ObservableCollection<Client> Clients { get; } = new();
        public ObservableCollection<StatusRequest> StatusRequests { get; } = new();

        private StatusRequest? _selectedRequestStatus;

        public StatusRequest? SelectedRequestStatus
        {
            get => _selectedRequestStatus;
            set
            {
                SetProperty(ref _selectedRequestStatus, value);
                Request.StatusRequest = value?.StatusRequestsId ?? 0;
            }
        }

        public async void LoadOrderStatuses()
        {
            var statuses = await _orderRepository.GetAllStatusRequestAsync();
            StatusRequests.Clear();
            foreach (var status in statuses)
            {
                StatusRequests.Add(status);
            }
        }

        public RelayCommand SaveRequestCommand { get; }
        public RelayCommand CancelCommand { get; private set; }

        private async void OnSaveRequest()
        {
            await _orderRepository.AddRequestAsync(Request);
            Done?.Invoke();
        }

        internal void OnCancel()
        {
            Done?.Invoke();
        }

        public event Action? Done;
    }
}
