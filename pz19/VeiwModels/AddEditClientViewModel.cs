using pz19.Services;
using pz19;
using pz19.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz19.ViewModels
{
    class AddEditClientViewModel: BindableBase
    {
        private IClientRepository _repository;
        public AddEditClientViewModel(IClientRepository repo)
        {
            _repository = repo;
            SaveCommand = new RelayCommand(OnSave, CanSave);
            CancelCommand = new RelayCommand(OnCancel);
        }
        private bool _isEditeMode;
        public bool IsEditeMode
        {
            get => _isEditeMode;
            set => SetProperty(ref _isEditeMode, value);
        }

        private Client _editingClient = null;
        private ValidableClient _customer;
        public ValidableClient Client
        {
            get => _customer;
            set => SetProperty(ref _customer, value);
        }

        public RelayCommand SaveCommand { get; private set; }
        public RelayCommand CancelCommand { get; private set; }
        public event Action Done;

        //----------------

        private void OnCanExecuteChanges(object sender, EventArgs e)
        {
            SaveCommand.OnCanExecuteChanged();
        }

        private void CopyClient(Client source, ValidableClient target)
        {
            target.Id = source.ClientId;
            if (IsEditeMode)
            {
                target.Fio = source.Fio;
                target.Phone = source.Phone;
            }
        }

        internal void SetClient(Client customer)
        {
            _editingClient = customer;
            if (Client != null)
                Client.ErrorsChanged -= OnCanExecuteChanges;
            Client = new ValidableClient();
            Client.ErrorsChanged += OnCanExecuteChanges;
            CopyClient(customer, Client);
        }

        internal void OnCancel()
        {
            Done?.Invoke();
        }
        private bool CanSave() => !Client.HasErrors;

        private void UpdateClient(ValidableClient source, Client target)
        {
            target.Fio = source.Fio;
            target.Phone = source.Phone;
        }

        private async void OnSave()
        {
            UpdateClient(Client, _editingClient);
            if (IsEditeMode)
                await _repository.UpdateClientAsync(_editingClient);
            else
                await _repository.AddClientAsync(_editingClient);
            Done?.Invoke();
        }
}
}
