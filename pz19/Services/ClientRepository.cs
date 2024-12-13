using Microsoft.EntityFrameworkCore;
using pz19.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz19.Services
{
    public class ClientRepository : IClientRepository
    {
        private readonly DemoRequestContext _context = new DemoRequestContext();

        public async Task<Client> AddClientAsync(Client client)
        {
            _context.Clients.Add(client);
            await _context.SaveChangesAsync();
            return client;
        }

        public async Task DeleteClientAsync(int clientId)
        {
            var client = _context.Clients
                .FirstOrDefault(x => x.ClientId == clientId);
            if(client!= null) 
            {
                _context.Clients.Remove(client);
            }
            await _context.SaveChangesAsync();
        }

        public Task<Client> GetClientByIdAsync(int clientId)
        {
            return _context.Clients.FirstOrDefaultAsync(x => x.ClientId == clientId);
        }

        public Task<List<Client>> GetClientsAsync()
        {
            return _context.Clients.ToListAsync();
        }

        public async Task<Client> UpdateClientAsync(Client client)
        {
            if(!_context.Clients.Local.Any(x => x.ClientId == client.ClientId))
            {
                _context.Clients.Attach(client);
            }
            _context.Entry(client).State = EntityState.Modified;  
            await _context.SaveChangesAsync();
            return client;
        }
    }
}
