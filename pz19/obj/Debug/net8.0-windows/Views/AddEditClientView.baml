   M S B A M L   `   `   `  ����?  ;pz19, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:pz19.Views
pz19.Views  5      )clr-namespace:pz19.Modelspz19.Models  5      1clr-namespace:pz19.Converterspz19.Converters  5      "    pz19.Views.AddEditClientView   5      W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5      R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5      Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5      @d2http://schemas.microsoft.com/expression/blend/2008     5      *localclr-namespace:pz19.Views     5      *dataclr-namespace:pz19.Models     5      3	converterclr-namespace:pz19.Converters     5        � 	Resources  5      0  *pz19.Converters.ValueToVisibilityConverter   VisConverter  NegVisConverter   Negate �� 
TargetType �� Triggers P� Property P� Value �� Property �� Value �� Self	 �� RelativeSource
 �� Path��%"  &	        &	      '�� 1       5   
   6/     5   
   $
 True��6G   �� 5   
   " ��6    5      P� 5       �@=�HasError$
 True��69   . �5      ��  �@�ToolTip 5      �� #	 Z $2
 ,(Validation.Errors).CurrentItem.ErrorContent�5      5      5      .��5      �  � RowDefinitions 5   
   �� 5      $
��auto �6   �� 5      $
��auto �6   �� 5      $
��auto �6   .+�5"   
   � -   5        $��
ClientGrid��5"      $��0��6!   $��5q�5$      $��Center=�5%       � DataContext 5#      �� *Client+ 5'      �� 5(      $
��auto �6    �� 5)      $
��auto �6    �� 5*      $
��auto �6    �� 5+      $
��auto �6     � ColumnDefinitions 5-      �� 5.      $
��auto �6#   �� 5/      $
��auto �6#   .+�53      �� $��Имя:��6   $��0��54      $��0��55      $��Center=�56      $��Right=�57      $��3q�58      �� 59      $��0��5:      $��1��5;      $	��200��5<      $��3q�5=      ��59      �� *Fio+" �� ValidatesOnNotifyDataErrors$
 True��5=      �� 5C      $��Телефон:��6   $��2��5D      $��0��5E      $��Center=�5F      $��Right=�5G      $��3q�5H      �� 5I      $��2��5J      $��1��5K      $	��200��5L      $��3q�5M      ��5I      �� *Phone+$
 True��5M      5P      � 5R   
   -   5        $��CommandButtonsGrid��5R      $��1��6)   $��10q�5S      .+�5T      �� $��отмена��6   $	��250��5V      ��5U      �� *CancelCommand+5V      �� 5W      $��добавить��6   $	��250��5Z      ��5X      �� *SaveCommand+y�5Y      �� *IsEditeMode+ �� 	Converter# [ 5Z      �� 5[      $��сохранить��6   $	��250��5^      ��5\      �� *SaveCommand+y�5]      �� *IsEditeMode+# [  5^      5a      � 5c   
   -   5        $��CommandButtonsGrid2��5c      $��2��6*   $��10q�5d      .+�5e      �� $��отмена��6   $	��250��5g      ��5f      �� *CancelCommand+5g      5k      5m      5n      