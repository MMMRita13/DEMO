using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace demm1
{
    /// <summary>
    /// Логика взаимодействия для EditWindow.xaml
    /// </summary>
    public partial class EditWindow : Window
    {
        RemontEntities db = new RemontEntities();
        Request request;
        public EditWindow(Request requestToEdit)
        {
            InitializeComponent();
            //Получаем список всех механиков
            MasterCB.ItemsSource = db.User.Where(r => r.Type.Name == "Автомеханик").ToList();

            request = db.Request.Find(requestToEdit.ID);


            //Получаем список всех статусов
            StatusCB.ItemsSource = db.requestStatus.ToList();

           DetailTB.Text = request.repairParts;
            MasterCB.SelectedValue = request.masterID;
            StatusCB.SelectedValue = request.requestStatusID;
        }

        private void SaveButton_Click(object sender, RoutedEventArgs e)
        {
            if (MasterCB.SelectedValue is int masterId)
            {
                if (StatusCB.SelectedValue is int requestStatusID)
                {

                    if (request != null)
                    {
                        request.repairParts = DetailTB.Text;
                        request.masterID = masterId;
                        request.requestStatusID = requestStatusID;

                        if (requestStatusID == 3) //если заявка в статусе "готова" - добавляем дату выполнения (текущую)
                        {
                           request.completionDate = DateTime.Now;
                        }

                        db.Entry(request).State = EntityState.Modified;
                        db.SaveChanges();


                        MessageBox.Show("Заказ успешно обновлен!");

                        this.Close();
                    }
                }
                else
                {
                    MessageBox.Show("Выберите статус заказа");
                    return;
                }
            }
            else
            {
                MessageBox.Show("Выберите мастера");
                return;
            }

        }
    }
}
