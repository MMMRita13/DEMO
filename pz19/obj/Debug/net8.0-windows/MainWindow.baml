   M S B A M L   `   `   `  ����?  ;pz19, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:pz19.Views
pz19.Views  5   
   1clr-namespace:pz19.ViewModelspz19.ViewModels  5      clr-namespace:pz19pz19  5          pz19.MainWindow   5   	   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5   	   R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *viewsclr-namespace:pz19.Views     5   
   4
viewModelsclr-namespace:pz19.ViewModels     5      $localclr-namespace:pz19     5        � Title$  
MainWindow��5
   	   $	��450��6   $	��800��6)    � 	Resources 5       �� DataType,  &pz19.ViewModels.AddEditClientViewModel"   pz19.Views.AddEditClientView%  pz19.ViewModels.ClientViewModel   pz19.Views.ClientView&   pz19.ViewModels.RequestViewModel   pz19.Views.RequestView)  #pz19.ViewModels.AddRequestViewModel   pz19.Views.AddRequestView��%,  (��       5      *, +)(�� 2     5      *, +)(�� d     5      *, +)(�� �     5      *, +)�� 5   
   "  6   .R�5        5      �� 5   
   "  6   .R�5        5      �� 5   
   "  6   .R�5        5      �� 5   
   "  6   .R�5        5      5       � DataContext 5      	  pz19.MainWindowViewModel	  5   
   .��5      �  � RowDefinitions 5   
   �� 5       $
��auto �6   �� 5!      $��* �6   .+�5$   
   � -   5         �Name$ NavigationBar��5$       � ColumnDefinitions 5%      �� 5&      $��* �6#   �� 5'      $��* �6#   .+�5*      �� $��клиенты��6   $��clients��5,   	   ��5+   	   �� *NavigationCommand+5,   	   �� 5.      $��заявки��6   $��
addRequest��50   	   $��1��51   	   ��5/   	   �� *NavigationCommand+51   	   52      � 53   
   -   5        $ ContentGrid��53      $��1��6$   .+�54      �� ��6   �� *ClientBBViewModel+55      56      57      