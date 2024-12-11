using Azure.Core;
using Microsoft.EntityFrameworkCore;
using pz19.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;
using Request = pz19.Models.Request;

namespace pz19.Services
{
    internal class RequestRepository :IRequestRepository
    {
        private readonly DemoRequestContext _context 
            = new DemoRequestContext();

        public async Task<Request> AddRequestAsync(Request request)
        {
            _context.Requests.Add(request);
            await _context.SaveChangesAsync();
            return request;
        }

        public async Task DeleteRequestAsync(long requestId)
        {
            using (TransactionScope scope = new TransactionScope()) 
            { 
                var request = _context.Requests.Include("(RequestItems")
                    .Include("(RequestItems.(RequestItemsOptions")
                    .FirstOrDefault(o => o.RequestId == requestId); 

                
                
                await _context.SaveChangesAsync();
                scope.Complete();
            }
        }

        public Task<List<Request>> GetAllRequestsAsync()=> _context.Requests.ToListAsync();
        

        public Task<List<HomeTechType>> GetAllHomeTechTypeAsync()=> _context.HomeTechTypes.ToListAsync();

        public Task<List<HomeTechModel>> GetAllHomeTechModelAsync()=> _context.HomeTechModels.ToListAsync();

        public Task<List<StatusRequest>> GetAllStatusRequestAsync() => _context.StatusRequests.ToListAsync();

        public Task<List<Request>> GetRequestsByClientAsync(int requestId) =>
            _context.Requests.Where(o => o.RequestId == requestId).ToListAsync();   

        public async Task<Request> UpdateRequestAsync(Request request)
        {
            if(!_context.Requests.Local.Any(o => o.RequestId == request.RequestId))
            {
                _context.Requests.Attach(request);
            }
            _context.Entry(request).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return request;
        }

        public Task<List<Request>> GetRequestsByClientAsync(Guid clientId)
        {
            throw new NotImplementedException();
        }
    }
}
