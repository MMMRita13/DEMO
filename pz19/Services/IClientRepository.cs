using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using pz19.Models;

namespace Pizza.Services
{
    public interface IClientRepository
    {
        //получать список всех пользователей
        Task<List<Client>> GetClientsAsync();
        //получать пользователя по id
        Task<Client> GetClientByIdAsync(int clientId);
        //обновлять пользователя
        Task<Client> UpdateClientAsync(Client client);
        //удалять пользователя
        Task DeleteClientAsync(int clientId);
        //создавать пользователя
        Task<Client> AddClientAsync(Client client);
        
    }
}
