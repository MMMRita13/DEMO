   M S B A M L   `   `   `  ���� ?  ;pz19, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:pz19.Views
pz19.Views  )clr-namespace:pz19.Modelspz19.Models  1clr-namespace:pz19.Converterspz19.Converters  "    pz19.Views.AddEditClientView   W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     @d2http://schemas.microsoft.com/expression/blend/2008     *localclr-namespace:pz19.Views     *dataclr-namespace:pz19.Models     3	converterclr-namespace:pz19.Converters       � 	Resources  0  *pz19.Converters.ValueToVisibilityConverter   VisConverter  NegVisConverter   Negate �� 
TargetType �� Triggers P� Property P� Value �� Property �� Value �� Self	 �� RelativeSource
 �� Path��%�   &	        &	      '��          $
 True���� " �� P�  �@=�HasError$
 True��. ���  �@�ToolTip �� #	 Z $2
 ,(Validation.Errors).CurrentItem.ErrorContent�.���  � RowDefinitions �� $
��auto ��� $
��auto ��� $
��auto �.+�� -   $��
ClientGrid��$��0��$��5q�$��Center=� � DataContext �� *Client+ �� $
��auto ��� $
��auto ��� $
��auto ��� $
��auto � � ColumnDefinitions �� $
��auto ��� $
��auto �.+��� $��Имя:��$��0��$��0��$��Center=�$��Right=�$��3q��� $��0��$��1��$	��200��$��3q����� *Fio+" �� ValidatesOnNotifyDataErrors$
 True���� $��Телефон:��$��2��$��0��$��Center=�$��Right=�$��3q��� $��2��$��1��$	��200��$��3q����� *Phone+$
 True��� -   $��CommandButtonsGrid��$��1��$��10q�.+��� $��отмена��$	��250������ *CancelCommand+�� $��добавить��$	��250������ *SaveCommand+y��� *IsEditeMode+ �� 	Converter# [ �� $��сохранить��$	��250������ *SaveCommand+y��� *IsEditeMode+# [  � -   $��CommandButtonsGrid2��$��2��$��10q�.+��� $��отмена��$	��250������ *CancelCommand+