using pz19.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz19.Services
{
    public interface IRequestRepository
    {
        //получить список заказов конкретного клиента
        Task<List<Request>> GetRequestsByClientAsync(int clientId);
        //получить список всех заказов в системе
        Task<List<Request>> GetAllRequestsAsync();
        //добавить заказ
        Task<Request> AddRequestAsync(Request request);
        //обновить заказ
        Task<Request> UpdateRequestAsync(Request request);
        //удалить заказ
        Task DeleteRequestAsync(long requestId);

        //получить список всех типов техники
        Task<List<HomeTechType>> GetAllHomeTechTypeAsync();
        //получить список моделей техники
        Task<List<HomeTechModel>> GetAllHomeTechModelAsync();
        //получить список статуса заявок
        Task<List<StatusRequest>> GetAllStatusRequestAsync();
    }
}
