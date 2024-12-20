﻿using pz19;
using pz19.Models;
using pz19.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;

namespace pz19.ViewModels
{
    class RequestViewModel: BindableBase
    {
        private IRequestRepository _repository;
        public RequestViewModel(RequestRepository repository)
        {
            _repository = repository;
            Requests = new ObservableCollection<Request>();
            LoadAllRequests();
            PlaceRequestedCommands = new RelayCommand<Request>(OnPlaceRequest);
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
        private ObservableCollection<Request>? _requests;
        public ObservableCollection<Request>? Requests
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

        public async Task LoadRequests()
        {
            var requests = await _repository.GetRequestsByClientAsync(SelectedClient.ClientId);

            foreach (var request in requests)
            {
                Requests.Add(request);
            }
        }

        private List<Request>? _listr;

        public async void LoadAllRequests()
        {
            _listr = await _repository.GetAllRequestsAsync();
            Requests = new ObservableCollection<Request>(_listr);
            //if (SelectedClient == null)
            //{
                
                //var requests = await _repository.GetAllRequestsAsync();
                //Requests1.Clear();
                //foreach (var request in requests)
                //{
                //    Requests1.Add(request);
                //}
            //}
        }

        public RelayCommand<Request> PlaceRequestedCommands { get; private set; }
        public event Action<Request> PlaceRequested = delegate { };

        public void PlaceRequestedCommand(Request request)
        {
            PlaceRequested(request);
        }

        private void OnPlaceRequest(Request request)
        {
            PlaceRequested(request);
        }
    }
}
