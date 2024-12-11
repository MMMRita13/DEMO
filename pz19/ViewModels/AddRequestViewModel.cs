
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

            SaveOrderCommand = new RelayCommand(OnSaveOrder);
            CancelCommand = new RelayCommand(OnCancel);

            LoadOrderStatuses();
        }

        private Request _order;

        public Request Request
        {
            get => _order;
            set => SetProperty(ref _order, value);
        }

        private Client? _selectedCustomer;

        public Client? SelectedCustomer
        {
            get => _selectedCustomer;
            set
            {
                SetProperty(ref _selectedCustomer, value);
                Request.ClientId = value?.ClientId ?? Guid.Empty;
            }
        }

        public ObservableCollection<Client> Customers { get; } = new();
        public ObservableCollection<StatusRequest> StatusRequests { get; } = new();

        private StatusRequest? _selectedOrderStatus;

        public StatusRequest? SelectedOrderStatus
        {
            get => _selectedOrderStatus;
            set
            {
                SetProperty(ref _selectedOrderStatus, value);
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

        public RelayCommand SaveOrderCommand { get; }
        public RelayCommand CancelCommand { get; private set; }

        private async void OnSaveOrder()
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
